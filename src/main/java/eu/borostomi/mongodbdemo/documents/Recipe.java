package eu.borostomi.mongodbdemo.documents;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("recipes")
@Data
@NoArgsConstructor
public class Recipe {

    @Id
    private String id;
    private String name;
    private Integer prepTime;
    private String prepUnit;
    private String difficulty;
    private List<Step> steps;
    private List<Ingredient> ingredients;
    private List<String> materials;
}
