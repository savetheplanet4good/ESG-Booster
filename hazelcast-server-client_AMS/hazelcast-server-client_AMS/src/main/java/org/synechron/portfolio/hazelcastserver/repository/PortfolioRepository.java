package org.synechron.portfolio.hazelcastserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.synechron.esg.model.Portfolio;



@Repository("portfolioRepository")
public interface PortfolioRepository extends MongoRepository<Portfolio, String> {

	Optional<Portfolio> findByPortfolioId(String portfolioId);

	void deleteByPortfolioId(String portfolioId);
	
}
