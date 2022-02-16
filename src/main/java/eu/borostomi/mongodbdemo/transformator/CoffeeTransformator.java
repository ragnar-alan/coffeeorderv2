package eu.borostomi.mongodbdemo.transformator;

import eu.borostomi.mongodbdemo.documents.Coffee;
import eu.borostomi.mongodbdemo.documents.Ingredient;
import eu.borostomi.mongodbdemo.documents.Recipe;
import eu.borostomi.mongodbdemo.dto.CoffeeDto;
import eu.borostomi.mongodbdemo.dto.IngredientDto;
import eu.borostomi.mongodbdemo.dto.RecipesDto;
import eu.borostomi.mongodbdemo.request.CoffeeRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CoffeeTransformator {

    public static final String IMPERIAL = "imperial";
    private TransformatorCommandProvider transformatorCommandProvider;

    public CoffeeTransformator(TransformatorCommandProvider transformatorCommandProvider) {
        this.transformatorCommandProvider = transformatorCommandProvider;
    }

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
            IngredientDto dto = new IngredientDto();
            dto.setName(ingredient.getName());
            UnitTransformator transformator = transformatorCommandProvider.getTransformator(ingredient.getUnit());
            if (measurement.equals(IMPERIAL)) {
                dto.setAmount(transformator.convert(ingredient.getAmount()));
                dto.setUnit(transformator.convertUnit(ingredient.getUnit()));
            } else {
                dto.setAmount(BigDecimal.valueOf(ingredient.getAmount()).setScale(2, RoundingMode.HALF_UP));
                dto.setUnit(ingredient.getUnit());
            }

            dto.setIsReplaceable(ingredient.getReplaceable());
            return dto;
        }).collect(Collectors.toList());
    }

    public Coffee convertRequestToEntity(CoffeeRequest request, String measurement) {
        Coffee coffee = new Coffee();
        coffee.setName(request.getName());
        coffee.setAromaProfile(request.getAromaProfile());
        coffee.setAromaNotes(request.getAromaNotes());
        coffee.setCupSize(request.getCupSize());
        coffee.setTasteIntensity(request.getTasteIntensity());
        coffee.setRecipes(request.getRecipes());
        coffee.setCollection(request.getIsCollection());
        coffee.setPrice(request.getPrice());
        coffee.setOrderable(request.getIsOrderable());
        coffee.setIsDecaff(request.getIsDecaff());
        return coffee;
    }
}
