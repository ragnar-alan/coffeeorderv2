package eu.borostomi.mongodbdemo.controller;

import eu.borostomi.mongodbdemo.dto.CoffeeDto;
import eu.borostomi.mongodbdemo.request.BaseCoffeeRequest;
import eu.borostomi.mongodbdemo.request.CoffeeRequestWithId;
import eu.borostomi.mongodbdemo.service.CoffeeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoffeeController {

    private final CoffeeService coffeeService;

    public CoffeeController(final CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping(path = "/coffee-details/{coffeeName}")
    public ResponseEntity<CoffeeDto> getCoffeeDetails(
            @PathVariable final String coffeeName,
            @CookieValue(name = "measurement", required = false) final String measurement) {
        return coffeeService.getCoffeeByName(coffeeName, measurement);
    }

    @PostMapping(path = "/coffee-details/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CoffeeDto> createCoffee(@RequestBody final BaseCoffeeRequest request) {
        return coffeeService.createCoffee(request);
    }

    @PutMapping(path = "/coffee-details/{coffeeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CoffeeDto> updateCoffee(
            @PathVariable final String coffeeId,
            @RequestBody final CoffeeRequestWithId request) {
        coffeeService.updateCoffee(request, coffeeId);
        return null;
    }

    @DeleteMapping(path = "/coffee-details/{coffeeId}")
    public ResponseEntity<String> deleteCoffee(@PathVariable final String coffeeId) {
        return coffeeService.deleteCoffee(coffeeId);
    }
}
