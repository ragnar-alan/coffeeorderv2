package eu.borostomi.mongodbdemo.documents;

import eu.borostomi.mongodbdemo.model.ShortRecipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document("deleted-coffees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletedCoffee {

    @Id
    private String id;
    private String name;
    private String aromaProfile;
    private String aromaNotes;
    private List<String> cupSize;
    private Integer tasteIntensity;
    private List<ShortRecipe> recipes;
    private Boolean collection;
    private BigDecimal price;
    private Boolean orderable;
    private Boolean isDecaff;
}
