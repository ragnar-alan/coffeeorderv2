package eu.borostomi.mongodbdemo.service;

import eu.borostomi.mongodbdemo.documents.Recipe;
import eu.borostomi.mongodbdemo.dto.RecipeDto;
import eu.borostomi.mongodbdemo.repository.RecipeRepository;
import eu.borostomi.mongodbdemo.transformator.RecipeTransformator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeTransformator recipeTransformator;

    public RecipeService(RecipeRepository recipeRepository, RecipeTransformator recipeTransformator) {
        this.recipeRepository = recipeRepository;
        this.recipeTransformator = recipeTransformator;
    }

    public ResponseEntity<RecipeDto> getRecipeByName(String recipeName, String measurement) {
        Recipe recipe = recipeRepository.findByName(recipeName);
        RecipeDto recipeDto = recipeTransformator.convertEntityToDto(recipe);
        return new ResponseEntity<RecipeDto>(HttpStatus.OK);
    }
}
