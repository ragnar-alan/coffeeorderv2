package eu.borostomi.mongodbdemo.controller;

import eu.borostomi.mongodbdemo.service.CoffeeService;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoffeeController {

    private final CoffeeService coffeeService;

    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping(path = "/coffee-details/{coffeeName}")
    public String home(@PathVariable String coffeeName, @CookieValue(name = "measurement", required = false) String measurement) {
        return coffeeService.getCoffeeByName(coffeeName, measurement);
    }
}
