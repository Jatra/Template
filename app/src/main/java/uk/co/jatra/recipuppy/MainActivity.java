package uk.co.jatra.recipuppy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.searchview) SearchView searchView;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    private RecipeAdapter adapter;

    private List<String> recipes = new ArrayList(Arrays.asList("one", "two", "three"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    recipes.add(newText);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipeAdapter();
        recyclerView.setAdapter(adapter);
    }


    private class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder>{

        @Override
        public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final TextView view = (TextView)LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
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

        public RecipeViewHolder(TextView view) {
            super(view);
            this.view = view;
        }

        public TextView getView() {
            return view;
        }
    }
}
