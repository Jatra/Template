package uk.co.jatra.template;

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
import io.reactivex.disposables.CompositeDisposable;
import uk.co.jatra.template.api.RecipeFetcher;

public class MainActivity extends AppCompatActivity implements TemplateView {

    public static final String ERROR_FETCHING_DATA = "Error fetching data ... ";
    @BindView(R.id.searchview) SearchView searchView;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;

    private DataAdapter adapter;
    private List<String> data = Collections.emptyList();
    private CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        disposable = new CompositeDisposable();

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
                    disposable.add(fetcher.getRecipesByKeyword(newText, MainActivity.this));

                } else {
                    clearData();
                }
                return true;
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DataAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposable.clear();
    }

    @Override
    public void setValues(List<String> values) {
        this.data = values;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(Throwable throwable) {
        Toast.makeText(this, ERROR_FETCHING_DATA +throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void clearData() {
        data.clear();
        adapter.notifyDataSetChanged();
    }

    private class DataAdapter extends RecyclerView.Adapter<DataViewHolder> {

        @Override
        public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new DataViewHolder(view);
        }

        @Override public void onBindViewHolder(DataViewHolder holder, int position) {
            holder.getView().setText(data.get(position));
        }

        @Override public int getItemCount() {
            return data.size();
        }
    }

    private class DataViewHolder extends RecyclerView.ViewHolder {
        private final TextView view;

        DataViewHolder(TextView view) {
            super(view);
            this.view = view;
        }

        TextView getView() {
            return view;
        }
    }
}
