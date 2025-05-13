package ph.edu.usc.skillboost.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.moduleDescription.setText(module.getDescription());

        // Change background color based on selection
        if (module.isSelected()) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.selected_background));
        } else {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.cardview_light_background));
        }

        // Handle click to toggle selection
        holder.itemView.setOnClickListener(v -> {
            module.toggleSelected();
            notifyItemChanged(position); // Update the item
        });
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    static class ModuleViewHolder extends RecyclerView.ViewHolder {
        TextView moduleTitle, moduleDescription;
        CardView cardView;

        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            moduleTitle = itemView.findViewById(R.id.module_title);
            moduleDescription = itemView.findViewById(R.id.module_description);
            cardView = (CardView) itemView;
        }
    }
}
