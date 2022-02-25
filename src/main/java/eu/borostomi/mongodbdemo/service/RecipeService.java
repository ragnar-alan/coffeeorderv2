package eu.borostomi.mongodbdemo.service;

import eu.borostomi.mongodbdemo.documents.Recipe;
import eu.borostomi.mongodbdemo.dto.RecipeDto;
import eu.borostomi.mongodbdemo.repository.RecipeRepository;
import eu.borostomi.mongodbdemo.request.BaseRecipeRequest;
import eu.borostomi.mongodbdemo.request.RecipeRequest;
import eu.borostomi.mongodbdemo.transformator.RecipeTransformator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class RecipeService {

    public static final String RECIPE_NOT_FOUND_ID = "Recipe not found with this id: ";
    public static final String METRIC = "metric";
    private final RecipeRepository recipeRepository;
    private final RecipeTransformator recipeTransformator;

    public RecipeService(final RecipeRepository recipeRepository,
                         final RecipeTransformator recipeTransformator) {
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
        Recipe recipe = new Recipe();
        recipeTransformator.convertRequestToEntity(request, METRIC, recipe);
        Recipe recipeResult;
        try {
            recipeResult = recipeRepository.save(recipe);
            RecipeDto dtoResult = recipeTransformator.convertEntityToDto(recipeResult, METRIC);
            return ResponseEntity.ok(dtoResult);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<RecipeDto> updateRecipe(final RecipeRequest request,
                                                  final String recipeId,
                                                  final String measurement) {
        ResponseEntity<RecipeDto> responseEntityResult;
        Optional<Recipe> existingRecipe = recipeRepository.findById(recipeId);
        if (existingRecipe.isPresent()) {
            try {
                Recipe convertedRequest =
                        recipeTransformator.convertRequestToEntity(request, measurement, existingRecipe.get());
                RecipeDto result =
                        recipeTransformator.convertEntityToDto(recipeRepository.save(convertedRequest), measurement);
                responseEntityResult = ResponseEntity.ok(result);
            } catch (Exception e) {
                responseEntityResult = ResponseEntity.badRequest().build();
            }
        } else {
            responseEntityResult = ResponseEntity.notFound().build();
        }
        return responseEntityResult;
    }

    public ResponseEntity<RecipeDto> deleteRecipe(final String recipeId) {
        ResponseEntity<RecipeDto> responseEntityResult;
        try {
            Optional<Recipe> recipe = recipeRepository.findById(recipeId);
            if (recipe.isPresent()) {
                recipeRepository.delete(recipe.get());
                responseEntityResult = ResponseEntity.noContent().build();
            } else {
                responseEntityResult = ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            responseEntityResult = ResponseEntity.badRequest().build();
        }
        return responseEntityResult;
    }
}
