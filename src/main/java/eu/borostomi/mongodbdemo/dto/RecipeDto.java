package eu.borostomi.mongodbdemo.dto;

import eu.borostomi.mongodbdemo.documents.Step;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class RecipeDto {
    private String id;
    private String name;
    private Integer prepTime;
    private String prepUnit;
    private String difficulty;
    private List<String> materials;
    private List<IngredientDto> ingredients;
    private List<Step> steps;
}
