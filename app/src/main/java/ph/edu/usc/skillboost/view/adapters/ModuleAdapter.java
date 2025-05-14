package ph.edu.usc.skillboost.view.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.model.Module;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder> {

    private List<Module> moduleList;
    private Context context;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public ModuleAdapter(Context context, List<Module> moduleList) {
        this.context = context;
        this.moduleList = moduleList;
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.module_item, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        Module module = moduleList.get(position);

        holder.moduleTitle.setText(module.getTitle());
        holder.moduleDescription.setText(module.getContent());

        // Show or hide the description based on selection
        if (position == selectedPosition) {
            holder.moduleDescription.setVisibility(View.VISIBLE);
            holder.toggleIndicator.setImageResource(R.drawable.caret_up); // Collapse icon
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.selected_background));
        } else {
            holder.moduleDescription.setVisibility(View.GONE);
            holder.toggleIndicator.setImageResource(R.drawable.caret_down); // Expand icon
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.cardview_light_background));
        }

        // Handle click to toggle selection
        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = (selectedPosition == position) ? RecyclerView.NO_POSITION : position; // Toggle selection
            notifyItemChanged(previousPosition); // Refresh the previously selected item
            notifyItemChanged(selectedPosition); // Refresh the newly selected item
        });
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    static class ModuleViewHolder extends RecyclerView.ViewHolder {
        TextView moduleTitle, moduleDescription;
        ImageView toggleIndicator;
        CardView cardView;

        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            moduleTitle = itemView.findViewById(R.id.module_title);
            moduleDescription = itemView.findViewById(R.id.module_description);
            toggleIndicator = itemView.findViewById(R.id.toggle_indicator);
            cardView = itemView.findViewById(R.id.module_card);
        }
    }
}