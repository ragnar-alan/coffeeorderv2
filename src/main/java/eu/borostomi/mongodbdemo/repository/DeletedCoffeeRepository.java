package eu.borostomi.mongodbdemo.repository;

import eu.borostomi.mongodbdemo.documents.DeletedCoffee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedCoffeeRepository extends MongoRepository<DeletedCoffee, String> {
}
