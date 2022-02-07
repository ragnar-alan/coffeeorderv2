package eu.borostomi.mongodbdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@ToString
public class IngredientDto {
    private String name;
    private BigDecimal amount;
    private String unit;
    private Boolean isReplaceable;
}
