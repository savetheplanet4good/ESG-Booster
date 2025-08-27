package org.synechron.portfolio.service;

import org.synechron.portfolio.response.dto.CompanyPeerAverageUIResponseDto;
import java.io.IOException;

public interface CompanyPeerAverageService {

	CompanyPeerAverageUIResponseDto getPeerAverageData(String portfolioId, String isin) throws IOException;
}

