package eu.borostomi.mongodbdemo.transformator;

import eu.borostomi.mongodbdemo.documents.Recipe;
import eu.borostomi.mongodbdemo.dto.RecipeDto;
import eu.borostomi.mongodbdemo.request.BaseRecipeRequest;
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

    public <T extends BaseRecipeRequest> Recipe convertRequestToEntity(
            final T request,
            final String measurement,
            final Recipe recipe) {
        return setRecipe(request, measurement, recipe);
    }

    private <T extends BaseRecipeRequest> Recipe setRecipe(
            final T request,
            final String measurement,
            final Recipe recipe) {
        recipe.setName(request.getName());
        recipe.setDifficulty(request.getDifficulty());
        recipe.setIngredients(
                unitConverterUtility.convertIngredientsDtoToEntity(request.getIngredients(), measurement));
        recipe.setMaterials(request.getMaterials());
        recipe.setSteps(request.getSteps());
        recipe.setPrepTime(request.getPrepTime());
        recipe.setPrepUnit(request.getPrepUnit());
        return recipe;
    }
}
