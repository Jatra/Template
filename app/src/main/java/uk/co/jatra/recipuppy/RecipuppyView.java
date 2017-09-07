package uk.co.jatra.recipuppy;

import java.util.List;

public interface RecipuppyView {
    void setRecipes(List<String> titles);
    void showError(Throwable throwable);
}
