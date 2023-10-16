package eu.borostomi.mongodbdemo.documents;

import eu.borostomi.mongodbdemo.model.ShortRecipe;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document("coffees")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Coffee {
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
