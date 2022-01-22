package eu.borostomi.mongodbdemo.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MainRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainRepository.class);

    public String getMain() {
        LOGGER.info("Hi");
        return "repository";
    }
}
    Espresso Con Pana