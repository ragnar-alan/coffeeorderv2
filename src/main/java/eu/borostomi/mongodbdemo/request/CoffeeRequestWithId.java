package eu.borostomi.mongodbdemo.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class CoffeeRequestWithId extends BaseCoffeeRequest{
    private String id;
}
