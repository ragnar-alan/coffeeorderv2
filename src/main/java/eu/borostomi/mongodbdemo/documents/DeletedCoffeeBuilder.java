package eu.borostomi.mongodbdemo.documents;

import eu.borostomi.mongodbdemo.model.ShortRecipe;

import java.math.BigDecimal;
import java.util.List;

public class DeletedCoffeeBuilder {
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

    public DeletedCoffeeBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public DeletedCoffeeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public DeletedCoffeeBuilder setAromaProfile(String aromaProfile) {
        this.aromaProfile = aromaProfile;
        return this;
    }

    public DeletedCoffeeBuilder setAromaNotes(String aromaNotes) {
        this.aromaNotes = aromaNotes;
        return this;
    }

    public DeletedCoffeeBuilder setCupSize(List<String> cupSize) {
        this.cupSize = cupSize;
        return this;
    }

    public DeletedCoffeeBuilder setTasteIntensity(Integer tasteIntensity) {
        this.tasteIntensity = tasteIntensity;
        return this;
    }

    public DeletedCoffeeBuilder setRecipes(List<ShortRecipe> recipes) {
        this.recipes = recipes;
        return this;
    }

    public DeletedCoffeeBuilder setCollection(Boolean collection) {
        this.collection = collection;
        return this;
    }

    public DeletedCoffeeBuilder setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public DeletedCoffeeBuilder setOrderable(Boolean orderable) {
        this.orderable = orderable;
        return this;
    }

    public DeletedCoffeeBuilder setIsDecaff(Boolean isDecaff) {
        this.isDecaff = isDecaff;
        return this;
    }

    public DeletedCoffee build() {
        return new DeletedCoffee(id, name, aromaProfile, aromaNotes, cupSize, tasteIntensity, recipes, collection, price, orderable, isDecaff);
    }
}
