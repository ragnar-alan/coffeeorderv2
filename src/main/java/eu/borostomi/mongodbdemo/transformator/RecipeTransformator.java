package eu.borostomi.mongodbdemo.transformator;

import eu.borostomi.mongodbdemo.documents.Recipe;
import eu.borostomi.mongodbdemo.dto.RecipeDto;
import org.springframework.stereotype.Component;

@Component
public class RecipeTransformator {
    private final UnitConverterUtility unitConverterUtility;

    public RecipeTransformator(final UnitConverterUtility unitConverterUtility) {
        this.unitConverterUtility = unitConverterUtility;
    }

    public RecipeDto convertEntityToDto(final Recipe recipe, final String measurement) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipe.getId());
        recipeDto.setName(recipe.getName());
        recipeDto.setDifficulty(recipe.getDifficulty());
        recipeDto.setIngredients(unitConverterUtility.convertIngredients(recipe.getIngredients(), measurement));
        recipeDto.setMaterials(recipe.getMaterials());
        recipeDto.setSteps(recipe.getSteps());
        recipeDto.setPrepTime(recipe.getPrepTime());
        recipeDto.setPrepUnit(recipe.getPrepUnit());
        return recipeDto;
    }
}
