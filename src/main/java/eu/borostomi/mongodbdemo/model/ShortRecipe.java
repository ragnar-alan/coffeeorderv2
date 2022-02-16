package eu.borostomi.mongodbdemo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShortRecipe {
    private String name;
    private Integer prepTime;
    private String prepUnit;
    private String difficulty;
}
