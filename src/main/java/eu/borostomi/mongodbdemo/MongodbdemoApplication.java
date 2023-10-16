package eu.borostomi.mongodbdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;

@SpringBootApplication
@Cacheable
public class MongodbdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongodbdemoApplication.class, args);
    }

}
