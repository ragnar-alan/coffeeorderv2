package eu.borostomi.mongodbdemo.documents;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("steps")
@Data
@NoArgsConstructor
public class Step {
    @Id
    private String id;
    private String step;
}
