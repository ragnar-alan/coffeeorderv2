package eu.borostomi.mongodbdemo.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("ingredients")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Ingredient {
    private String name;
    private Double amount;
    private String unit;
    private Boolean replaceable;
}
