package uk.co.jatra.recipuppy.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import uk.co.jatra.recipuppy.domain.ResultList;


public interface PuppyApi {
    @GET("api")
    Single<ResultList> getRecipes(@Query("q") String keyword);
}
