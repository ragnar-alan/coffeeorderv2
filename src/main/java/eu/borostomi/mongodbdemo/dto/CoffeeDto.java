package eu.borostomi.mongodbdemo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@NoArgsConstructor
@ToString
@JsonInclude(Include.NON_NULL)
public class CoffeeDto {
    private String id;
    private String name;
    private String aromaProfile;
    private String aromaNotes;
    private List<String> cupSize;
    private Integer tasteIntensity;
    private RecipeDto recipe;
    private Boolean isCollection;
    private BigDecimal price;
    private Boolean isOrderable;
    private Boolean isDecaff;
}