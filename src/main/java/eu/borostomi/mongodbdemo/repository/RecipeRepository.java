package eu.borostomi.mongodbdemo.repository;

import eu.borostomi.mongodbdemo.documents.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
    Recipe findRecipeByName(String recipeName);
}
