package eu.borostomi.mongodbdemo.service;

import eu.borostomi.mongodbdemo.documents.Recipe;
import eu.borostomi.mongodbdemo.dto.RecipeDto;
import eu.borostomi.mongodbdemo.repository.RecipeRepository;
import eu.borostomi.mongodbdemo.request.BaseRecipeRequest;
import eu.borostomi.mongodbdemo.transformator.RecipeTransformator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class RecipeService {

    public static final String RECIPE_NOT_FOUND_ID = "Recipe not found with this id: ";
    private final RecipeRepository recipeRepository;
    private final RecipeTransformator recipeTransformator;

    public RecipeService(final RecipeRepository recipeRepository, final RecipeTransformator recipeTransformator) {
        this.recipeRepository = recipeRepository;
        this.recipeTransformator = recipeTransformator;
    }

    public ResponseEntity<RecipeDto> getRecipeByName(final String recipeName, final String measurement) {
        Recipe recipe;
        try {
            recipe = recipeRepository.findByName(recipeName);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        return ResponseEntity.ok(recipeTransformator.convertEntityToDto(recipe, measurement));
    }


    public ResponseEntity<RecipeDto> getRecipeById(final String id, final String measurement) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        Recipe recipe = recipeOptional
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, RECIPE_NOT_FOUND_ID + id));
        return ResponseEntity.ok(recipeTransformator.convertEntityToDto(recipe, measurement));
    }

    public ResponseEntity<RecipeDto> createRecipe(final BaseRecipeRequest request) {
        return null;
    }
}
