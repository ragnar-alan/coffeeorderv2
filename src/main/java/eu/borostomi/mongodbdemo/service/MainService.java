package eu.borostomi.mongodbdemo.service;

import eu.borostomi.mongodbdemo.repository.MainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainService.class);

    private final MainRepository mainRepository;

    public MainService(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public String getMain() {
        LOGGER.info("service");
        return mainRepository.getMain();
    }
}
