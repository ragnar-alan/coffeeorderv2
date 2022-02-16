package eu.borostomi.mongodbdemo.service;

import eu.borostomi.mongodbdemo.documents.Coffee;
import eu.borostomi.mongodbdemo.documents.Recipe;
import eu.borostomi.mongodbdemo.model.ShortRecipe;
import eu.borostomi.mongodbdemo.repository.CoffeeRepository;
import eu.borostomi.mongodbdemo.repository.RecipeRepository;
import eu.borostomi.mongodbdemo.request.CoffeeRequest;
import eu.borostomi.mongodbdemo.transformator.CoffeeTransformator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CoffeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeService.class);

    private final CoffeeRepository coffeeRepository;
    private final RecipeRepository recipeRepository;
    private final CoffeeTransformator coffeeTransformator;

    public CoffeeService(CoffeeRepository coffeeRepository, CoffeeTransformator coffeeTransformator, RecipeRepository recipeRepository) {
        this.coffeeRepository = coffeeRepository;
        this.recipeRepository = recipeRepository;
        this.coffeeTransformator = coffeeTransformator;
    }

    public String getCoffeeByName(String name, String measurement) {
        Coffee coffee = coffeeRepository.findByName(name);
        Recipe recipe;
        if (!coffee.getRecipes().isEmpty()) {
            recipe = recipeRepository.findRecipeByName(coffee.getRecipes().stream().filter(Objects::nonNull).map(ShortRecipe::getName).findFirst().get());
        } else {
            recipe = null;
        }
        return coffeeTransformator.convertCoffeeToDto(coffee, recipe, measurement).toString();
    }

    public Coffee createCoffee(CoffeeRequest request, String measurement) {
        Coffee convertedRequest = coffeeTransformator.convertRequestToEntity(request, measurement);
        Boolean coffeeExists = isCoffeeExists(request, convertedRequest);
        if (!coffeeExists) {
            return coffeeRepository.insert(convertedRequest);
        } else {
            throw new RuntimeException("Coffee already exists");
        }
    }

    private Boolean isCoffeeExists(CoffeeRequest request, Coffee convertedRequest) {
        Coffee coffee = coffeeRepository.findByName(request.getName());
        if (coffee == null) {
            return false;
        }
        return coffee.equals(convertedRequest);
    }
}
