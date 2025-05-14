package ph.edu.usc.skillboost.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ph.edu.usc.skillboost.R;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

    private final List<String> filters;
    private int selectedPosition = 0;
    private final OnFilterClickListener listener;

    public interface OnFilterClickListener {
        void onFilterClick(String filter);
    }

    public FilterAdapter(List<String> filters, OnFilterClickListener listener) {
        this.filters = filters;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String filter = filters.get(position);
        holder.filterName.setText(filter);

        // Set the visual state for the selected item
        holder.itemView.setSelected(position == selectedPosition);

        // Handle click events
        holder.itemView.setOnClickListener(v -> {
            if (selectedPosition != holder.getAdapterPosition()) {
                int oldPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(oldPosition);
                notifyItemChanged(selectedPosition);
                listener.onFilterClick(filter);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    public void setSelectedFilter(String filter) {
        int index = filters.indexOf(filter);
        if (index != -1) {
            selectedPosition = index;
            notifyDataSetChanged(); // This ensures that it re-binds and highlights correctly
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView filterName;

        ViewHolder(View itemView) {
            super(itemView);
            filterName = itemView.findViewById(R.id.filterName);
        }
    }
}
