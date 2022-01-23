package eu.borostomi.mongodbdemo.controller;

import eu.borostomi.mongodbdemo.service.MainService;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoffeeController {

    private final MainService mainService;

    public CoffeeController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping(path = "/coffee-details/{coffeeName}")
    public String home(@PathVariable String coffeeName, @CookieValue("measurement") String measurement) {
        return mainService.getCoffeeByName(coffeeName, measurement);
    }
}
