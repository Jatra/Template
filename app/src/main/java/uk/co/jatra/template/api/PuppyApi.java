package uk.co.jatra.template.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import uk.co.jatra.template.recipes.ResultList;


public interface PuppyApi {
    @GET("api")
    Single<ResultList> getRecipesByKeyword(@Query("q") String keyword);
}