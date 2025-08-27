package org.synechron.portfolio.hazelcastserver.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.synechron.esg.model.ApplicationComponentSettings;

@Repository("applicationComponentSettingsRepository")
public interface ApplicationComponentSettingsRepository extends MongoRepository<ApplicationComponentSettings, String>{

	Optional<ApplicationComponentSettings> findByPropertyDisplayName(String propertyDisplayName);
}
