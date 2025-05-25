package petexplorer.petexplorerclients;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import domain.utils.SearchResultWrapper;
import petexplorer.petexplorerclients.utils.ServerConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import service.ApiService;

public class RetrofitClient {
    // private static final String BASE_URL = "http://192.168.39.224:8080";
    private static final String BASE_URL = ServerConfig.BASE_URL;
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }

    public static class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

        public interface OnItemClickListener {
            void onItemClick(SearchResultWrapper item);
        }

        private final OnItemClickListener listener;
        private List<SearchResultWrapper> searchResults;

        public SearchAdapter(List<SearchResultWrapper> resultWrappers, OnItemClickListener listener) {
            this.listener = listener;
            this.searchResults = resultWrappers != null ? resultWrappers : new ArrayList<>();
        }


        public void submitList(List<SearchResultWrapper> newList) {
            if (newList == null) {
                newList = new ArrayList<>();
            }
            searchResults.clear();
            searchResults.addAll(newList);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
            return new SearchViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            holder.bind(searchResults.get(position), listener);
        }

        @Override
        public int getItemCount() {
            return searchResults.size();
        }

        public static class SearchViewHolder extends RecyclerView.ViewHolder {
            private final TextView nameTextView;
            private final TextView categoryTextView;

            public SearchViewHolder(@NonNull View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.nameTextView);
                categoryTextView = itemView.findViewById(R.id.categoryTextView);
            }

            public void bind(SearchResultWrapper item, OnItemClickListener listener) {
                nameTextView.setText(item.getName() != null ? item.getName() : "N/A");
                categoryTextView.setText(item.getCategory() != null ? item.getCategory() : "Unknown");

                itemView.setOnClickListener(v -> listener.onItemClick(item));
            }

        }
    }
}