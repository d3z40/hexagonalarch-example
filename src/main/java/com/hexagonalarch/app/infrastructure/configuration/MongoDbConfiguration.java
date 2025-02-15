package com.hexagonalarch.app.infrastructure.configuration;

import com.hexagonalarch.app.infrastructure.repository.SpringDataAnimalRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = SpringDataAnimalRepository.class)
public class MongoDbConfiguration {

}
