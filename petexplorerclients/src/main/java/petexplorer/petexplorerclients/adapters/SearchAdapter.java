package petexplorer.petexplorerclients.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import domain.utils.SearchResultDTO;
import petexplorer.petexplorerclients.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(SearchResultDTO item);
    }

    private final OnItemClickListener listener;
    private List<SearchResultDTO> searchResults;

    public SearchAdapter(List<SearchResultDTO> resultWrappers, OnItemClickListener listener) {
        this.listener = listener;
        this.searchResults = resultWrappers != null ? resultWrappers : new ArrayList<>();
    }


    public void submitList(List<SearchResultDTO> newList) {
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
        private final ImageView iconImageView;
        private final TextView nameTextView;
        private final TextView categoryTextView;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
        }

        public void bind(SearchResultDTO item, OnItemClickListener listener) {
            Context context = itemView.getContext();
            String name = item.getTitle() != null ? item.getTitle() : "N/A";
            String category = item.getType() != null ? item.getType() : "Unknown";
            String drawableName = category != null ? category.split(" ")[0].toLowerCase() : "";
            int drawableResId = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());

            if (drawableResId != 0) {
                iconImageView.setImageResource(drawableResId);
            } else {
                System.out.println("eroare");
            }

            nameTextView.setText(name);
            categoryTextView.setText(category);

            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }


    }
}
