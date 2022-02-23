package eu.borostomi.mongodbdemo.transformator;

import eu.borostomi.mongodbdemo.documents.Coffee;
import eu.borostomi.mongodbdemo.documents.Recipe;
import eu.borostomi.mongodbdemo.dto.CoffeeDto;
import eu.borostomi.mongodbdemo.dto.RecipeDto;
import eu.borostomi.mongodbdemo.request.BaseCoffeeRequest;
import eu.borostomi.mongodbdemo.request.CoffeeRequestWithId;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CoffeeTransformator {

    private final UnitConverterUtility unitConverterUtility;

    public CoffeeTransformator(final UnitConverterUtility unitConverterUtility) {
        this.unitConverterUtility = unitConverterUtility;
    }

    public CoffeeDto convertCoffeeToDto(final Coffee coffee, final Recipe recipe, final String measurement) {
        if (coffee == null) {
            return null;
        }
        CoffeeDto dto = new CoffeeDto();
        dto.setId(coffee.getId());
        dto.setName(coffee.getName());
        dto.setAromaProfile(coffee.getAromaProfile());
        dto.setAromaNotes(coffee.getAromaNotes());
        dto.setCupSize(coffee.getCupSize());
        dto.setTasteIntensity(coffee.getTasteIntensity());
        dto.setRecipe(convertRecipes(recipe, Objects.requireNonNullElse(measurement, "metric")));
        dto.setIsCollection(coffee.getCollection());
        dto.setPrice(coffee.getPrice());
        dto.setIsOrderable(coffee.getOrderable());
        dto.setIsDecaff(coffee.getIsDecaff());
        return dto;
    }

    private RecipeDto convertRecipes(final Recipe recipe, final String measurement) {
        if (recipe == null) {
            return null;
        }
        RecipeDto dto = new RecipeDto();
        dto.setName(recipe.getName());
        dto.setPrepTime(recipe.getPrepTime());
        dto.setPrepUnit(recipe.getPrepUnit());
        dto.setDifficulty(recipe.getDifficulty());
        dto.setMaterials(recipe.getMaterials());
        dto.setIngredients(unitConverterUtility.convertIngredients(recipe.getIngredients(), measurement));
        dto.setSteps(recipe.getSteps());
        return dto;
    }

    public Coffee convertRequestToEntity(final BaseCoffeeRequest request, final Coffee coffee) {
        setCoffeeFields(request, coffee);
        return coffee;
    }

    public Coffee convertUpdateRequestToEntity(final CoffeeRequestWithId request, final Coffee coffee) {
        coffee.setId(request.getId());
        setCoffeeFields(request, coffee);
        return coffee;
    }

    private void setCoffeeFields(final BaseCoffeeRequest request, final Coffee coffee) {
        coffee.setName(request.getName());
        coffee.setAromaProfile(request.getAromaProfile());
        coffee.setAromaNotes(request.getAromaNotes());
        coffee.setCupSize(request.getCupSize());
        coffee.setTasteIntensity(request.getTasteIntensity());
        coffee.setRecipes(request.getRecipes());
        coffee.setCollection(request.getIsCollection());
        coffee.setPrice(request.getPrice());
        coffee.setOrderable(request.getOrderable());
        coffee.setIsDecaff(request.getIsDecaff());
    }
}
