package eu.borostomi.mongodbdemo.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("steps")
public class Step {
    @Id
    private String id;
    private String step;
}
