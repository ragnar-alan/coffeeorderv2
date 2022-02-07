package eu.borostomi.mongodbdemo.service;

import eu.borostomi.mongodbdemo.documents.Coffee;
import eu.borostomi.mongodbdemo.documents.Recipe;
import eu.borostomi.mongodbdemo.repository.CoffeeRepository;
import eu.borostomi.mongodbdemo.repository.RecipeRepository;
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
            recipe = recipeRepository.findRecipeByName(coffee.getRecipes().stream().filter(Objects::nonNull).map(Recipe::getName).findFirst().get());
        } else {
            recipe = null;
        }
        return coffeeTransformator.convertCoffeeToDto(coffee, recipe, measurement).toString();
    }
}
