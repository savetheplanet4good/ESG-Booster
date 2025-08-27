from __future__ import annotations

import hashlib
import os
from typing import List, Dict, Any, Optional, Tuple

import polars as pl

DATA_CURATED_DIR = os.path.join("data", "curated")
SCORES_PARQUET = os.path.join(DATA_CURATED_DIR, "scores.parquet")


def _scale_byte_to_score(byte_val: int) -> float:
    return round((byte_val / 255.0) * 100.0, 1)


def score_company_placeholder(ticker: str) -> Dict[str, Any]:
    digest = hashlib.sha1(ticker.upper().encode("utf-8")).digest()
    e = _scale_byte_to_score(digest[0])
    s = _scale_byte_to_score(digest[1])
    g = _scale_byte_to_score(digest[2])
    esg = round((e + s + g) / 3.0, 1)
    return {"ticker": ticker, "E": e, "S": s, "G": g, "ESG": esg, "confidence": 0.83}


def _load_scores_table() -> Optional[pl.DataFrame]:
    if os.path.exists(SCORES_PARQUET):
        return pl.read_parquet(SCORES_PARQUET)
    return None


def score_company(ticker: str) -> Dict[str, Any]:
    tbl = _load_scores_table()
    if tbl is not None and ticker in set(tbl["ticker"].to_list()):
        row = tbl.filter(pl.col("ticker") == ticker).to_dicts()[0]
        return {
            "ticker": ticker,
            "E": float(row.get("E", 0.0)),
            "S": float(row.get("S", 0.0)),
            "G": float(row.get("G", 0.0)),
            "ESG": float(row.get("ESG", 0.0)),
            "confidence": float(row.get("confidence", 0.0)),
        }
    return score_company_placeholder(ticker)


def compute_placeholder_scores(holdings: pl.DataFrame) -> List[Dict[str, Any]]:
    return [score_company(row["ticker"]) for row in holdings.to_dicts()]


def aggregate_portfolio_scores(holdings: pl.DataFrame, by_company: List[Dict[str, Any]]) -> Dict[str, float]:
    if holdings.height == 0:
        return {"E": 0.0, "S": 0.0, "G": 0.0, "ESG": 0.0, "confidence": 0.0}
    weights = {r["ticker"]: float(r["weight"]) for r in holdings.to_dicts()}
    total_w = sum(weights.values()) or 1.0
    def wavg(metric: str) -> float:
        acc = 0.0
        for row in by_company:
            acc += weights.get(row["ticker"], 0.0) * float(row[metric])
        return round(acc / total_w, 1)
    E = wavg("E")
    S = wavg("S")
    G = wavg("G")
    ESG = round((E + S + G) / 3.0, 1)
    conf = wavg("confidence")
    return {"E": E, "S": S, "G": G, "ESG": ESG, "confidence": conf}


def compute_stub_scores(holdings: pl.DataFrame) -> List[Dict[str, Any]]:
    """Return neutral stub scores (all pillars 50.0) for Sprint 1 endpoint.

    This intentionally ignores any curated scores to keep Sprint 1 deterministic
    and independent of seeded data.
    """
    result: List[Dict[str, Any]] = []
    for row in holdings.to_dicts():
        result.append({
            "ticker": row["ticker"],
            "E": 50.0,
            "S": 50.0,
            "G": 50.0,
            "ESG": 50.0,
            "confidence": 0.83,
        })
    return result


def _max_pillar_gap(row: Dict[str, Any]) -> Tuple[str, str, float]:
    """Return (pillar_max, pillar_min, gap_value) for E/S/G of a company row."""
    pillars = {"E": float(row.get("E", 0.0)), "S": float(row.get("S", 0.0)), "G": float(row.get("G", 0.0))}
    pillar_max = max(pillars, key=pillars.get)
    pillar_min = min(pillars, key=pillars.get)
    gap = round(pillars[pillar_max] - pillars[pillar_min], 1)
    return pillar_max, pillar_min, gap


def compute_outliers(by_company: List[Dict[str, Any]], *, discordance_threshold: float = 30.0, low_conf_threshold: float = 0.5) -> List[Dict[str, Any]]:
    """
    Identify simple outliers based on cross-pillar discordance and low confidence.

    - discordance: max(E,S,G) - min(E,S,G) >= discordance_threshold
    - low confidence: confidence < low_conf_threshold

    Returns a list of {ticker, reasons: [...], details: {...}}.
    """
    outliers: List[Dict[str, Any]] = []
    for row in by_company:
        reasons: List[str] = []
        details: Dict[str, Any] = {}
        # Cross-pillar discordance
        max_p, min_p, gap = _max_pillar_gap(row)
        if gap >= discordance_threshold:
            reasons.append("cross_pillar_discordance")
            details.update({"pillar_max": max_p, "pillar_min": min_p, "gap": gap})
        # Low confidence
        conf = float(row.get("confidence", 0.0))
        if conf < low_conf_threshold:
            reasons.append("low_confidence")
            details.update({"confidence": conf})
        if reasons:
            outliers.append({
                "ticker": row.get("ticker"),
                "reasons": reasons,
                "details": details,
            })
    return outliers
