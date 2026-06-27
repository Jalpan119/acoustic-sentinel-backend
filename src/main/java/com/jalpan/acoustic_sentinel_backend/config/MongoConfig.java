package com.jalpan.acoustic_sentinel_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.jalpan.acoustic_sentinel_backend.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        // This physically forces the driver to use this specific database name
        return "acoustic_sentinel";
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}