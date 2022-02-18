package eu.borostomi.mongodbdemo.transformator;

import eu.borostomi.mongodbdemo.documents.Recipe;
import eu.borostomi.mongodbdemo.dto.RecipeDto;
import org.springframework.stereotype.Component;

@Component
public class RecipeTransformator {
    public RecipeDto convertEntityToDto(Recipe recipe) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipe.getId());
        recipeDto.setName(recipe.getName());
        recipeDto.setDifficulty(recipe.getDifficulty());
        recipeDto.setIngredients(recipe.getIngredients());
        recipeDto.setMaterials(recipe.getMaterials());
        recipeDto.setSteps(recipe.getSteps());
        recipeDto.setPrepTime(recipe.getPrepTime());
        recipeDto.setPrepUnit(recipe.getPrepUnit());
        return recipeDto;
    }
}
