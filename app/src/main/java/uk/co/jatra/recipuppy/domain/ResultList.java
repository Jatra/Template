package uk.co.jatra.recipuppy.domain;

//From http://www.jsonschema2pojo.org/
//almost all(!) YAGNI for now.

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultList {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("version")
    @Expose
    private Double version;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("results")
    @Expose
    private List<Recipe> recipes = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

}