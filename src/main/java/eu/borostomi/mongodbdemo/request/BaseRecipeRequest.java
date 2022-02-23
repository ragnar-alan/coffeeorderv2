package eu.borostomi.mongodbdemo.request;

import eu.borostomi.mongodbdemo.documents.Step;
import eu.borostomi.mongodbdemo.dto.IngredientDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class BaseRecipeRequest {
    private String name;
    private Integer prepTime;
    private String prepUnit;
    private String difficulty;
    private List<String> materials;
    private List<IngredientDto> ingredients;
    private List<Step> steps;
}
