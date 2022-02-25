package eu.borostomi.mongodbdemo.controller;

import eu.borostomi.mongodbdemo.dto.RecipeDto;
import eu.borostomi.mongodbdemo.request.BaseRecipeRequest;
import eu.borostomi.mongodbdemo.request.RecipeRequest;
import eu.borostomi.mongodbdemo.service.RecipeService;
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
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping(path = "/recipe-details/{recipeName}")
    public ResponseEntity<RecipeDto> getRecipeDetails(
            @PathVariable final String recipeName,
            @CookieValue(name = "measurement", required = false) final String measurement) {
        return recipeService.getRecipeByName(recipeName, measurement);
    }

    @GetMapping(path = "/recipe-details/{recipeId}")
    public ResponseEntity<RecipeDto> getRecipeDetailsById(
            @PathVariable final String recipeId,
            @CookieValue(name = "measurement", required = false) final String measurement) {
        return recipeService.getRecipeById(recipeId, measurement);
    }

    @PostMapping(path = "/recipe-details/create")
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody final BaseRecipeRequest request) {
        return recipeService.createRecipe(request);
    }

    @PutMapping(path = "/recipe-detauls/update/{recipeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeDto> updateRecipe(
            @RequestBody final RecipeRequest request,
            @PathVariable final  String recipeId,
            @CookieValue(name = "measurement", required = false)final  String measurement) {
        return recipeService.updateRecipe(request, recipeId, measurement);
    }

    @DeleteMapping(path = "/recipe-details/delete/{recipeId}")
    public ResponseEntity<RecipeDto> deleteRecipe(@PathVariable final String recipeId) {
        return recipeService.deleteRecipe(recipeId);
    }
}
