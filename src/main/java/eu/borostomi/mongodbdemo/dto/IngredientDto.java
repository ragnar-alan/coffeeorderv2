package eu.borostomi.mongodbdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class IngredientDto {
    private String name;
    private Integer amount;
    private String unit;
    private Boolean isReplaceable;
}
