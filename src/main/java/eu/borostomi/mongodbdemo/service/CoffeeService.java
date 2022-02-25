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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Service
@SuppressWarnings("HiddenFieldCheck")
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;
    private final RecipeRepository recipeRepository;
    private final CoffeeTransformator coffeeTransformator;
    private final DeletedCoffeeRepository deletedCoffeeRepository;

    public CoffeeService(final CoffeeRepository coffeeRepository,
                         final CoffeeTransformator coffeeTransformator,
                         final RecipeRepository recipeRepository,
                         final DeletedCoffeeRepository deletedCoffeeRepository) {
        this.coffeeRepository = coffeeRepository;
        this.recipeRepository = recipeRepository;
        this.coffeeTransformator = coffeeTransformator;
        this.deletedCoffeeRepository = deletedCoffeeRepository;
    }

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
        ResponseEntity<CoffeeDto> responseEntityResult;
        Coffee coffeeExists = isCoffeeExistsById(coffeeId);
        if (coffeeExists != null) {
            try {
                Coffee convertedUpdateRequest = coffeeTransformator.convertUpdateRequestToEntity(request, coffeeExists);
                Recipe recipe = getRecipe(convertedUpdateRequest);
                CoffeeDto result = coffeeTransformator.convertCoffeeToDto(
                        coffeeRepository.save(convertedUpdateRequest), recipe, null);
                responseEntityResult = ResponseEntity.ok(result);
            } catch (Exception ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
            }
        } else {
            responseEntityResult = ResponseEntity.notFound().build();
        }
        return responseEntityResult;
    }

    @Transactional
    public ResponseEntity<String> deleteCoffee(final String coffeeId) {
        Coffee coffee = getCoffeeById(coffeeId);
        ResponseEntity<String> responseEntityResult;
        if (coffee != null) {
            coffee.setOrderable(false);
            DeletedCoffee deletedCoffee = buildDeletedCoffee(coffee);
            try {
                deletedCoffeeRepository.save(deletedCoffee);
                coffeeRepository.deleteById(coffeeId);
                responseEntityResult = ResponseEntity.noContent().build();
            } catch (Exception ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
            }
        } else {
            responseEntityResult = ResponseEntity.notFound().build();
        }
        return responseEntityResult;
    }

    private DeletedCoffee buildDeletedCoffee(final Coffee coffee) {
        return new DeletedCoffeeBuilder()
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
            recipe = recipeRepository.findByName(
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
