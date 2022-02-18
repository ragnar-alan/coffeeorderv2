package eu.borostomi.mongodbdemo.dto;

import eu.borostomi.mongodbdemo.documents.Ingredient;
import eu.borostomi.mongodbdemo.documents.Step;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecipeDto {
    private String id;
    private String name;
    private String difficulty;
    private List<String> materials;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private Integer prepTime;
    private String prepUnit;
}
