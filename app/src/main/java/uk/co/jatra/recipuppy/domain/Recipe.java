package uk.co.jatra.recipuppy.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//From http://www.jsonschema2pojo.org/
//Mostly(!) YAGNI for now.

public class Recipe {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("ingredients")
    @Expose
    private String ingredients;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}

