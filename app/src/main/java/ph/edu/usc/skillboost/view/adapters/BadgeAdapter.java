package ph.edu.usc.skillboost.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ph.edu.usc.skillboost.model.Badge;
import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.utils.Utilities;

public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder> {
    private List<Badge> badgeList;
    private List<Badge> allBadges;  // Save the original list of badges
    private Context context;

    public BadgeAdapter(Context context, List<Badge> badgeList) {
        this.context = context;
        this.badgeList = badgeList;
        this.allBadges = new ArrayList<>(badgeList);  // Keep a copy of the original list
    }

    @NonNull
    @Override
    public BadgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_certificate, parent, false);
        return new BadgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BadgeViewHolder holder, int position) {
        Badge badge = badgeList.get(position);
        holder.tvTitle.setText(badge.getTitle());
        holder.tvDesc.setText(badge.getDescription());
        holder.image.setImageResource(Utilities.getDrawableFromRes(
            context,
            badge.getImageRes()
        ));
    }

    @Override
    public int getItemCount() {
        return badgeList.size();
    }

    // Method to filter by keyword and category
    public void filter(String keyword, String category) {
        List<Badge> filteredBadges = new ArrayList<>();

        for (Badge badge : allBadges) {
            boolean matchesKeyword = badge.getTitle().toLowerCase().contains(keyword.toLowerCase());
            boolean matchesCategory = category.equals("All") || badge.getCategories().contains(category);  // Add your category logic here

            if (matchesKeyword && matchesCategory) {
                filteredBadges.add(badge);
            }
        }

        updateBadgeList(filteredBadges);
    }

    // Method to update the badge list
    public void updateBadgeList(List<Badge> filteredBadges) {
        this.badgeList = filteredBadges;
        notifyDataSetChanged();
    }

    public static class BadgeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc;
        ImageView image;

        public BadgeViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            image = itemView.findViewById(R.id.ivBadge);
        }
    }
}
