package eu.borostomi.mongodbdemo.documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("coffees")
public class Coffee {
    private String id;
    private String name;
    private Integer prepTime;
    private String prepUnit;
    private String difficulty;
    private String[] materials;
    private Ingredient[] ingredients;
    private DescriptionStep[] description;
}
