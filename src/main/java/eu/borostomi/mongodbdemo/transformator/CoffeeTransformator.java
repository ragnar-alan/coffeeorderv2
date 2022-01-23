package eu.borostomi.mongodbdemo.transformator;

import com.digidemic.unitof.UnitOf;
import eu.borostomi.mongodbdemo.documents.Coffee;
import eu.borostomi.mongodbdemo.documents.Ingredient;
import eu.borostomi.mongodbdemo.documents.Recipe;
import eu.borostomi.mongodbdemo.dto.CoffeeDto;
import eu.borostomi.mongodbdemo.dto.IngredientDto;
import eu.borostomi.mongodbdemo.dto.RecipesDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CoffeeTransformator {

    public CoffeeDto convertCoffeeToDto(Coffee coffee, Recipe recipe, String measurement) {
        CoffeeDto dto = new CoffeeDto();
        dto.setId(coffee.getId());
        dto.setName(coffee.getName());
        dto.setAromaProfile(coffee.getAromaProfile());
        dto.setAromaNotes(coffee.getAromaNotes());
        dto.setCupSize(coffee.getCupSize());
        dto.setTasteIntensity(coffee.getTasteIntensity());
        dto.setRecipe(convertRecipes(recipe, measurement));
        dto.setIsCollection(coffee.getCollection());
        dto.setPrice(coffee.getPrice());
        dto.setIsOrderable(coffee.getOrderable());
        dto.setIsDecaff(coffee.getIsDecaff());
        return dto;
    }

    private RecipesDto convertRecipes(Recipe recipe, String measurement) {
        if (recipe == null) {
            return null;
        }
        RecipesDto dto = new RecipesDto();
        dto.setName(recipe.getName());
        dto.setPrepTime(recipe.getPrepTime());
        dto.setPrepUnit(recipe.getPrepUnit());
        dto.setDifficulty(recipe.getDifficulty());
        dto.setMaterials(recipe.getMaterials());
        dto.setIngredients(convertIngredients(recipe.getIngredients(), measurement));
        dto.setSteps(recipe.getSteps());
        return dto;
    }

    private List<IngredientDto> convertIngredients(List<Ingredient> ingredients, String measurement) {
        return ingredients.stream().filter(Objects::nonNull).map(ingredient -> {
            /* TODO UnitOf investigation */
            IngredientDto dto = new IngredientDto();
            dto.setName(ingredient.getName());
            dto.setAmount(ingredient.getAmount());
            dto.setUnit(ingredient.getUnit());
            dto.setIsReplaceable(ingredient.getReplaceable());
            return dto;
        }).collect(Collectors.toList());
    }
}
