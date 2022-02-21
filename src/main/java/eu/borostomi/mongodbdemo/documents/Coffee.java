package eu.borostomi.mongodbdemo.documents;

import eu.borostomi.mongodbdemo.model.ShortRecipe;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document("coffees")
@Data
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coffee coffee = (Coffee) o;

        if (name != null ? !name.equals(coffee.name) : coffee.name != null) return false;
        if (tasteIntensity != null ? !tasteIntensity.equals(coffee.tasteIntensity) : coffee.tasteIntensity != null)
            return false;
        return isDecaff != null ? isDecaff.equals(coffee.isDecaff) : coffee.isDecaff == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (tasteIntensity != null ? tasteIntensity.hashCode() : 0);
        result = 31 * result + (isDecaff != null ? isDecaff.hashCode() : 0);
        return result;
    }
}
