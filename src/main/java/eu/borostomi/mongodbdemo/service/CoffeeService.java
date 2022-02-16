package eu.borostomi.mongodbdemo.service;

import eu.borostomi.mongodbdemo.documents.Coffee;
import eu.borostomi.mongodbdemo.documents.Recipe;
import eu.borostomi.mongodbdemo.model.ShortRecipe;
import eu.borostomi.mongodbdemo.repository.CoffeeRepository;
import eu.borostomi.mongodbdemo.repository.RecipeRepository;
import eu.borostomi.mongodbdemo.request.BaseCoffeeRequest;
import eu.borostomi.mongodbdemo.request.CoffeeRequestWithId;
import eu.borostomi.mongodbdemo.transformator.CoffeeTransformator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

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

    public Coffee createCoffee(BaseCoffeeRequest request) {
        Coffee convertedRequest = coffeeTransformator.convertRequestToEntity(request, new Coffee());
        Boolean coffeeExists = isCoffeeExistsByName(request, convertedRequest);
        if (!coffeeExists) {
            return coffeeRepository.insert(convertedRequest);
        } else {
            throw new RuntimeException("Coffee already exists");
        }
    }

    public Coffee updateCoffee(CoffeeRequestWithId request, String coffeeId) {
        Coffee coffeeExists = isCoffeeExistsById(coffeeId);
        Coffee convertedUpdateRequest = coffeeTransformator.convertRequestToEntity(request, coffeeExists);
        if (coffeeExists != null) {
            return coffeeRepository.save(convertedUpdateRequest);
        } else {
            throw new RuntimeException("Coffee not exists");
        }
    }

    private Boolean isCoffeeExistsByName(BaseCoffeeRequest request, Coffee convertedRequest) {
        Coffee coffee = coffeeRepository.findByName(request.getName());
        if (coffee == null) {
            return false;
        }
        return coffee.equals(convertedRequest);
    }

    private Coffee isCoffeeExistsById(String coffeeId) {
        Optional<Coffee> coffee = coffeeRepository.findById(coffeeId);
        return coffee.orElse(null);
    }
}
