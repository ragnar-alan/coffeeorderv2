package eu.borostomi.mongodbdemo.transformator;

import eu.borostomi.mongodbdemo.documents.Ingredient;
import eu.borostomi.mongodbdemo.dto.IngredientDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UnitConverterUtility {
    private static final String IMPERIAL = "imperial";
    private final ConverterProvider converterProvider;

    public UnitConverterUtility(final ConverterProvider converterProvider) {
        this.converterProvider = converterProvider;
    }

    public List<IngredientDto> convertIngredients(final List<Ingredient> ingredients, final String measurement) {
        return ingredients.stream().filter(Objects::nonNull).map(ingredient -> {
            IngredientDto dto = new IngredientDto();
            dto.setName(ingredient.getName());
            UnitConverter transformator = converterProvider.getTransformator(ingredient.getUnit());
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
}
