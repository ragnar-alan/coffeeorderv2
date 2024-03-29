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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@SuppressWarnings("HiddenFieldCheck")
@Slf4j
public class CoffeeService {
    private static final String COFFEE_NOT_EXIST_DELETE = "The coffee you want to delete is not exist. Coffee id: ";
    private static final String CANNOT_DELETE_COFFEE = "Cannot delete coffee:";
    private static final String COFFEE_DELETED = "Coffee deleted";

    private final CoffeeRepository coffeeRepository;
    private final RecipeRepository recipeRepository;
    private final CoffeeTransformator coffeeTransformator;
    private final DeletedCoffeeRepository deletedCoffeeRepository;

    @Autowired
    private CacheManager cacheManager;

    public CoffeeService(final CoffeeRepository coffeeRepository,
                         final CoffeeTransformator coffeeTransformator,
                         final RecipeRepository recipeRepository,
                         final DeletedCoffeeRepository deletedCoffeeRepository) {
        this.coffeeRepository = coffeeRepository;
        this.recipeRepository = recipeRepository;
        this.coffeeTransformator = coffeeTransformator;
        this.deletedCoffeeRepository = deletedCoffeeRepository;
    }

    @Cacheable(value="coffeeCache")
    public ResponseEntity<CoffeeDto> getCoffeeByName(final String name, final String measurement) {
        Coffee coffee = coffeeRepository.findByName(name);
        Recipe recipe = getRecipe(coffee);
        CoffeeDto result = coffeeTransformator.convertCoffeeToDto(coffee, recipe, measurement);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Cacheable(value="coffeeCache")
    public ResponseEntity<List<CoffeeDto>> getAllCoffee() {
        List<Coffee> results = coffeeRepository.findAll();
        return new ResponseEntity<>(coffeeTransformator.convertAllCoffeeToDto(results), HttpStatus.OK);
    }

    public Coffee getCoffeeById(final String id) {
        Optional<Coffee> coffee = coffeeRepository.findById(id);
        return coffee.orElse(null);
    }

    public ResponseEntity<CoffeeDto> createCoffee(final BaseCoffeeRequest request) {
        Coffee convertedRequest = coffeeTransformator.convertRequestToEntity(request, new Coffee());
        Boolean coffeeExists = isCoffeeExistsByName(request, convertedRequest);
        if (!coffeeExists) {
            CoffeeDto result = coffeeTransformator.convertCoffeeToDto(
                    coffeeRepository.insert(convertedRequest), null, null);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<CoffeeDto> updateCoffee(final CoffeeRequestWithId request, final String coffeeId) {
        Coffee coffeeExists = isCoffeeExistsById(coffeeId);
        if (coffeeExists != null) {
            try {
                Coffee convertedUpdateRequest = coffeeTransformator.convertUpdateRequestToEntity(request, coffeeExists);
                Recipe recipe = getRecipe(convertedUpdateRequest);
                CoffeeDto result = coffeeTransformator.convertCoffeeToDto(
                        coffeeRepository.save(convertedUpdateRequest), recipe, null);
                return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<String> deleteCoffee(final String coffeeId) {
        Coffee coffee = getCoffeeById(coffeeId);
        if (coffee == null) {
            return new ResponseEntity<>(COFFEE_NOT_EXIST_DELETE + coffeeId, HttpStatus.NOT_FOUND);
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
            result = new ResponseEntity<>(COFFEE_DELETED, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            result = new ResponseEntity<>(CANNOT_DELETE_COFFEE
                    + coffee.getName()
                    + " " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return result;
    }

    private Boolean isCoffeeExistsByName(final BaseCoffeeRequest request, final Coffee convertedRequest) {
        Coffee coffee = coffeeRepository.findByName(request.getName());
        if (coffee == null) {
            return false;
        }
        return coffee.equals(convertedRequest);
    }

    private Coffee isCoffeeExistsById(final String coffeeId) {
        Optional<Coffee> coffee = coffeeRepository.findById(coffeeId);
        return coffee.orElse(null);
    }

    private Recipe getRecipe(final Coffee coffee) {
        Recipe recipe;
        if (!coffee.getRecipes().isEmpty()) {
            recipe = recipeRepository.findRecipeByName(
                    coffee.getRecipes().stream()
                            .filter(Objects::nonNull)
                            .map(ShortRecipe::getName)
                            .findFirst()
                            .get());
        } else {
            recipe = null;
        }
        return recipe;
    }
}
