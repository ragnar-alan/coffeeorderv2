package eu.borostomi.mongodbdemo.repository;

import eu.borostomi.mongodbdemo.documents.Coffee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CoffeeRepository extends MongoRepository<Coffee, String> {

    Coffee findByName(String name);
    List<Coffee> findAll();
}