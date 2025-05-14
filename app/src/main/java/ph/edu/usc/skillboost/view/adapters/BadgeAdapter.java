package ph.edu.usc.skillboost.view.adapters;

import android.content.Context;
import android.util.Log;
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

public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder> {

    private List<Badge> badgeList;
    private final List<Badge> fullBadgeList;
    private final Context context;

    public BadgeAdapter(Context context, List<Badge> badgeList) {
        this.badgeList = badgeList;
        this.fullBadgeList = new ArrayList<>(badgeList);
        this.context = context;
    }

    @NonNull
    @Override
    public BadgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_certificate, parent, false);
        return new BadgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BadgeViewHolder holder, int position) {
        Badge badge = badgeList.get(position);
        holder.title.setText(badge.getName());
        holder.description.setText(badge.getDescription());

        String imageResName = badge.getImageUrl(); // Assume this is the name of the image file (e.g., "sample_certificate2")

        int imageResId = context.getResources().getIdentifier(imageResName, "drawable", context.getPackageName());

        if (imageResId == 0) {
            imageResId = R.drawable.sample_certificate2;
        }
        holder.image.setImageResource(imageResId);
    }

    @Override
    public int getItemCount() {
        return badgeList.size();
    }

    public void updateBadges(List<Badge> newBadges) {
        badgeList.clear();
        badgeList.addAll(newBadges);

        if (fullBadgeList.isEmpty()){
            fullBadgeList.addAll(newBadges); // Add all badges at the start
        }

        notifyDataSetChanged();
    }

    public void filter(String query) {
        badgeList.clear();
        if (query.isEmpty()) {
            badgeList.addAll(fullBadgeList); // If query is empty, restore the original list
        } else {
            for (Badge badge : fullBadgeList) {
                Log.d("BadgeAdapter", "Filtered Badge: " + badge.getName());
                if (badge.getName().toLowerCase().contains(query.toLowerCase())) {
                    badgeList.add(badge);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class BadgeViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView image;

        public BadgeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvCertificateTitle);
            description = itemView.findViewById(R.id.tvCertificateDescription);
            image = itemView.findViewById(R.id.imgCertificate);
        }
    }
}

