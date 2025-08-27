package org.synechron.portfolio.hazelcastserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.synechron.esg.model.AlternativesObject;
import org.synechron.esg.model.Portfolio;
import org.synechron.portfolio.response.dto.FailedInvestSuiteRequests;

import java.util.Optional;


@Repository("failedInvestSuiteRequestsRepository")
public interface FailedInvestSuiteRequestsRepository extends MongoRepository<FailedInvestSuiteRequests, Integer> {

	Optional<FailedInvestSuiteRequests> findByFailedInvestSuiteRequestsId(Integer id);    
	
	
}
