package eu.borostomi.mongodbdemo.service;

import eu.borostomi.mongodbdemo.documents.Coffee;
import eu.borostomi.mongodbdemo.documents.DeletedCoffee;
import eu.borostomi.mongodbdemo.documents.DeletedCoffeeBuilder;
import eu.borostomi.mongodbdemo.documents.Recipe;
import eu.borostomi.mongodbdemo.dto.CoffeeDto;
import eu.borostomi.mongodbdemo.model.ShortRecipe;
import eu.borostomi.mongodbdemo.repository.CoffeeRepository;
import eu.borostomi.mongodbdemo.repository.DeletedCoffeeRepository;
import eu.borostomi.mongodbdemo.repository.RecipeRepository;
import eu.borostomi.mongodbdemo.request.BaseCoffeeRequest;
import eu.borostomi.mongodbdemo.request.CoffeeRequestWithId;
import eu.borostomi.mongodbdemo.transformator.CoffeeTransformator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class CoffeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeService.class);

    private final CoffeeRepository coffeeRepository;
    private final RecipeRepository recipeRepository;
    private final CoffeeTransformator coffeeTransformator;
    private final DeletedCoffeeRepository deletedCoffeeRepository;

    public CoffeeService(CoffeeRepository coffeeRepository, CoffeeTransformator coffeeTransformator, RecipeRepository recipeRepository, DeletedCoffeeRepository deletedCoffeeRepository) {
        this.coffeeRepository = coffeeRepository;
        this.recipeRepository = recipeRepository;
        this.coffeeTransformator = coffeeTransformator;
        this.deletedCoffeeRepository = deletedCoffeeRepository;
    }

    public ResponseEntity<CoffeeDto> getCoffeeByName(String name, String measurement) {
        Coffee coffee = coffeeRepository.findByName(name);
        Recipe recipe = getRecipe(coffee);
        CoffeeDto result = coffeeTransformator.convertCoffeeToDto(coffee, recipe, measurement);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Coffee getCoffeeById(String id) {
        Optional<Coffee> coffee = coffeeRepository.findById(id);
        return coffee.orElse(null);
    }

    public ResponseEntity<CoffeeDto> createCoffee(BaseCoffeeRequest request) {
        Coffee convertedRequest = coffeeTransformator.convertRequestToEntity(request, new Coffee());
        Boolean coffeeExists = isCoffeeExistsByName(request, convertedRequest);
        if (!coffeeExists) {
            CoffeeDto result = coffeeTransformator.convertCoffeeToDto(coffeeRepository.insert(convertedRequest), null, null);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<CoffeeDto> updateCoffee(CoffeeRequestWithId request, String coffeeId) {
        Coffee coffeeExists = isCoffeeExistsById(coffeeId);
        if (coffeeExists != null) {
            try {
                Coffee convertedUpdateRequest = coffeeTransformator.convertUpdateRequestToEntity(request, coffeeExists);
                Recipe recipe = getRecipe(convertedUpdateRequest);
                CoffeeDto result = coffeeTransformator.convertCoffeeToDto(coffeeRepository.save(convertedUpdateRequest), recipe, null);
                return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<String> deleteCoffee(String coffeeId) {
        Coffee coffee = getCoffeeById(coffeeId);
        if (coffee == null) {
            return new ResponseEntity<>("The coffee you want to delete is not exist. Coffee id: " + coffeeId, HttpStatus.NOT_FOUND);
        }
        DeletedCoffee deletedCoffee = new DeletedCoffeeBuilder()
                .setId(coffee.getId())
                .setName(coffee.getName())
                .setAromaProfile(coffee.getAromaProfile())
                .setAromaNotes(coffee.getAromaNotes())
                .setCupSize(coffee.getCupSize())
                .setTasteIntensity(coffee.getTasteIntensity())
                .setRecipes(coffee.getRecipes())
                .setCollection(coffee.getCollection())
                .setPrice(coffee.getPrice())
                .setOrderable(coffee.getOrderable())
                .setIsDecaff(coffee.getIsDecaff())
                .build();
        coffee.setOrderable(false);
        ResponseEntity<String> result;
        try {
            deletedCoffeeRepository.save(deletedCoffee);
            coffeeRepository.deleteById(coffeeId);
            result = new ResponseEntity<>("Coffee deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            result = new ResponseEntity<>("Cannot delete coffee:" + coffee.getName() + " " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return result;
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

    private Recipe getRecipe(Coffee coffee) {
        Recipe recipe;
        if (!coffee.getRecipes().isEmpty()) {
            recipe = recipeRepository.findRecipeByName(coffee.getRecipes().stream().filter(Objects::nonNull).map(ShortRecipe::getName).findFirst().get());
        } else {
            recipe = null;
        }
        return recipe;
    }
}
