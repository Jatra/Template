package uk.co.jatra.template.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.jatra.template.TemplateView;
import uk.co.jatra.template.recipes.Recipe;
import uk.co.jatra.template.recipes.ResultList;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

public class RecipeFetcher {

    private static final int LIMIT = 20;
    public static final String RECIPE_PUPPY_BASE_URL = "http://www.recipepuppy.com/";
    private final PuppyApi service;

    public RecipeFetcher() {
        //debug logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        service = new Retrofit.Builder()
                .baseUrl(RECIPE_PUPPY_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(PuppyApi.class);
    }

    public Disposable getRecipesByKeyword(String keyword, TemplateView view) {
        return service.getRecipesByKeyword(keyword)
                .subscribeOn(io())
                .observeOn(mainThread())
                .flattenAsObservable(ResultList::getRecipes)
                .take(LIMIT)
                .map(Recipe::getTitle)
                .toList()
                .subscribe(view::setValues, view::showError);
    }
}
