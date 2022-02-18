package eu.borostomi.mongodbdemo.controller;

import eu.borostomi.mongodbdemo.dto.RecipeDto;
import eu.borostomi.mongodbdemo.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping(path = "/recipe-details/{recipeName}")
    public ResponseEntity<RecipeDto> getRecipeDetails(@PathVariable String recipeName, @CookieValue(name = "measurement", required = false) String measurement) {
        return recipeService.getRecipeByName(recipeName, measurement);
    }
}
