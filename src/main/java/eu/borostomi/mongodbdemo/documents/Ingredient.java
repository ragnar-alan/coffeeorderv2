package eu.borostomi.mongodbdemo.documents;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("ingredients")
@Data
public class Ingredient {
    private String name;
    private Double amount;
    private String unit;
    private Boolean replaceable;
}
