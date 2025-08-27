package org.synechron.portfolio.hazelcastserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.synechron.esg.model.AlternativesObject;
import org.synechron.esg.model.Portfolio;

import java.util.Optional;


@Repository("alternativeRepository")
public interface AlternativeRepository extends MongoRepository<AlternativesObject, Integer> {

	Optional<AlternativesObject> findByAlternativeId(int hashcode);

	
	
}
