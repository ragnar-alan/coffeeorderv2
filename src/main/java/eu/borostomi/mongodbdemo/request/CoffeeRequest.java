package eu.borostomi.mongodbdemo.request;

import eu.borostomi.mongodbdemo.dto.RecipesDto;
import eu.borostomi.mongodbdemo.model.ShortRecipe;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class CoffeeRequest {
    private String name;
    private String aromaProfile;
    private String aromaNotes;
    private List<String> cupSize;
    private Integer tasteIntensity;
    private List<ShortRecipe> recipes;
    private Boolean isCollection;
    private BigDecimal price;
    private Boolean isOrderable;
    private Boolean isDecaff;
}
