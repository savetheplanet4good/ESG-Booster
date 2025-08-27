package org.synechron.portfolio.hazelcastserver.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.synechron.esg.model.AppSettings;

@Repository("appsettingsRepository")
public interface AppSettingsRepository extends MongoRepository<AppSettings, String> {
    Optional<AppSettings> findByName(String name);
    
    void deleteByName(String name);
}
