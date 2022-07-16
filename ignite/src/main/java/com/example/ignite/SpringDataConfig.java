package com.example.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springdata22.repository.config.EnableIgniteRepositories;
import org.apache.ignite.transactions.TransactionConcurrency;
import org.apache.ignite.transactions.spring.SpringTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableIgniteRepositories
@EnableTransactionManagement
public class SpringDataConfig {

    @Bean
    public Ignite igniteInstance() {
        IgniteConfiguration config = new IgniteConfiguration();
        CacheConfiguration cache = new CacheConfiguration("PersonCache");
        cache.setIndexedTypes(Long.class, Person.class);
        config.setCacheConfiguration(cache);
        return Ignition.start(config);
    }

    @Bean
    public SpringTransactionManager transactionManager(Ignite node) {
        SpringTransactionManager mgr = new SpringTransactionManager();

        mgr.setConfiguration(new IgniteConfiguration()
            .setIgniteInstanceName("foo")
        );
        mgr.setTransactionConcurrency(TransactionConcurrency.OPTIMISTIC);
        return mgr;
    }
}