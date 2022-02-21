package eu.borostomi.mongodbdemo.repository;

import eu.borostomi.mongodbdemo.documents.Coffee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CoffeeRepository extends MongoRepository<Coffee, String> {

    Coffee findByName(String name);

    List<Coffee> findAll();
}