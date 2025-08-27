package org.synechron.portfolio.hazelcastserver.mapstore;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.synechron.esg.model.Portfolio;
import org.synechron.portfolio.hazelcastserver.repository.PortfolioRepository;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MapLoaderLifecycleSupport;
import com.hazelcast.core.MapStore;
import com.hazelcast.spring.context.SpringAware;


@SpringAware
@Component
public class PortfolioMapStore implements MapStore<String, Portfolio>,MapLoaderLifecycleSupport {
    
	Logger logger = LoggerFactory.getLogger(PortfolioMapStore.class);
	

	   @Autowired
	   private  PortfolioRepository portfolioRepository;
	    
	  public PortfolioMapStore() {
		  logger.info("Invoking the Portfolio Map Store ");
	  }
	    
	   
		@Override
		public Portfolio load(String key) {
			
			
				Optional<Portfolio> portfolio = portfolioRepository.findByPortfolioId(key);
		        return portfolio.isPresent() ? portfolio.get() : null;
			
		}

		@Override
		public Map<String, Portfolio> loadAll(Collection<String> keys) {
			// TODO Auto-generated method stub
			Map<String, Portfolio> result = new HashMap<>();
			logger.info("******************************      Loading All Data for Given Keys   ******************************");
	        for (String key : keys) {
	        	
	        	Portfolio portfolio = this.load(key);
	        	System.out.println(key + " portfolio is "+portfolio);
	            if (portfolio != null) {
	                result.put(key, portfolio);
	            }
	        }
	        return result;
		}

		@Override
		public Iterable<String> loadAllKeys() {
			 logger.info("******************************      Loading All keys   ******************************");
			// TODO Auto-generated method stub
			Map<String, Portfolio> portfolios = new HashMap<String, Portfolio>();
	        List<Portfolio> portfolioList = portfolioRepository.findAll();
	        logger.info("data is"+portfolioList.size());
	        logger.info("data is"+portfolioList);
	        for (Portfolio portfolio : portfolioList) {
	        	portfolios.put(portfolio.getPortfolioId(), portfolio);
	        }
	        return portfolios.keySet();
		}

	

		@Override
		public void store(String key, Portfolio portfolio) {
			
			logger.info("Persisiting the new record to databse" +portfolio);
			portfolioRepository.save(portfolio);
		}

		@Override
		public void storeAll(Map<String, Portfolio> map) {
			
			portfolioRepository.saveAll(new ArrayList<>(map.values()));
		}

		@Override
		public void delete(String key) {
			// TODO Auto-generated method stub
			portfolioRepository.deleteByPortfolioId(key);
		}

		@Override
		public void deleteAll(Collection<String> keys) {
			
			keys.forEach(portfolioRepository::deleteByPortfolioId);
		}


		@Override
		public void init(HazelcastInstance hazelcastInstance, Properties properties, String mapName) {
			
			 hazelcastInstance.getConfig().getManagedContext().initialize(this);
		}

		@Override
		public void destroy() {
			// kept it empty as there is no logic to execute on destroy
			
		}
		
}
