package uk.co.jatra.recipuppy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.jatra.recipuppy.api.RecipeFetcher;
import uk.co.jatra.recipuppy.domain.Recipe;
import uk.co.jatra.recipuppy.domain.ResultList;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

public class MainActivity extends AppCompatActivity {

    private static final int RECIPE_LIMIT = 20;
    @BindView(R.id.searchview) SearchView searchView;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private List<String> recipes = Collections.emptyList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        final RecipeFetcher fetcher = new RecipeFetcher();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    fetcher.getService().getRecipes(newText)
                            .subscribeOn(io())
                            .observeOn(mainThread())
                            .flattenAsObservable(ResultList::getRecipes)
                            .take(RECIPE_LIMIT)
                            .map(Recipe::getTitle)
                            .toList()
                            .subscribe(MainActivity.this::setRecipes, MainActivity.this::showError);
                } else {
                    clearRecipes();
                }
                return true;
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipeAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void setRecipes(List<String> recipes) {
        this.recipes = recipes;
        adapter.notifyDataSetChanged();
    }

    private void clearRecipes() {
        recipes.clear();
        adapter.notifyDataSetChanged();
    }

    private void showError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

        @Override
        public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new RecipeViewHolder(view);
        }

        @Override public void onBindViewHolder(RecipeViewHolder holder, int position) {
            holder.getView().setText(recipes.get(position));
        }

        @Override public int getItemCount() {
            return recipes.size();
        }
    }

    private class RecipeViewHolder extends RecyclerView.ViewHolder {
        private final TextView view;

        RecipeViewHolder(TextView view) {
            super(view);
            this.view = view;
        }

        TextView getView() {
            return view;
        }
    }
}
