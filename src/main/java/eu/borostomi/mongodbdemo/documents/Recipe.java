package eu.borostomi.mongodbdemo.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("recipes")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Recipe {

    @Id
    private String id;
    private String name;
    private Integer prepTime;
    private String prepUnit;
    private String difficulty;
    private List<String> materials;
    private List<Ingredient> ingredients;
    private List<String> steps;
}
