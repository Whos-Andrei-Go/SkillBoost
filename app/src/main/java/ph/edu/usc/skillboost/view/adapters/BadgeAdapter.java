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
import ph.edu.usc.skillboost.model.Course;
import ph.edu.usc.skillboost.utils.Utilities;

public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder> {
    public enum CardSize {
        SMALL, MEDIUM, LARGE
    }

    private List<Badge> badgeList;
    private List<Badge> allBadges;  // Save the original list of badges
    private CardSize cardSize;
    private Context context;

    public BadgeAdapter(Context context, List<Badge> badgeList) {
        this(context, badgeList, CardSize.SMALL); // Call the main constructor with default size
    }
    public BadgeAdapter(Context context, List<Badge> badgeList, CardSize cardSize) {
        this.context = context;
        this.badgeList = badgeList;
        this.cardSize = cardSize;
        this.allBadges = new ArrayList<>(badgeList);  // Keep a copy of the original list
    }

    @NonNull
    @Override
    public BadgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_certificate, parent, false);

        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;

        switch (cardSize) {
            case SMALL:
                width = Utilities.dpToPx(parent.getContext(), 200);
                height = Utilities.dpToPx(parent.getContext(), 180);
                break;
            case MEDIUM:
                width = Utilities.dpToPx(parent.getContext(), 230);
                height = Utilities.dpToPx(parent.getContext(), 300);
                break;
            case LARGE:
                width = Utilities.dpToPx(parent.getContext(), 340);
                height = Utilities.dpToPx(parent.getContext(), 280);
                break;
        }

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(width, height);
        layoutParams.setMargins(Utilities.dpToPx(context, 0), Utilities.dpToPx(context, 0), Utilities.dpToPx(context, 30), Utilities.dpToPx(context, 30));
        view.setLayoutParams(layoutParams);

        return new BadgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BadgeViewHolder holder, int position) {
        Badge badge = badgeList.get(position);
        holder.title.setText(badge.getTitle());
        holder.subtitle.setText(badge.getSubtitle());
        holder.image.setImageResource(Utilities.getDrawableFromRes(
            context,
            badge.getImageRes()
        ));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onBadgeClick(badge);
        });
    }

    @Override
    public int getItemCount() {
        return badgeList.size();
    }

    // Method to filter by keyword and category
    public void filter(String keyword) {
        List<Badge> filteredBadges = new ArrayList<>();

        for (Badge badge : allBadges) {
            boolean matchesKeyword = badge.getTitle().toLowerCase().startsWith(keyword.toLowerCase());

            if (matchesKeyword) {
                filteredBadges.add(badge);
            }
        }

        updateBadgeList(filteredBadges);
    }

    // Separate filter for category
    public void filterByCategory(String category) {
        category = category.toLowerCase();

        badgeList.clear();
        if (category.isEmpty() || category.equals("all")) {
            badgeList.addAll(allBadges); // If query is empty, restore the original list
        } else {
            for (Badge badge : allBadges) {
                List<String> categories = badge.getCategories();
                if (categories != null && categories.stream().map(String::toLowerCase).anyMatch(category::equals)) {
                    badgeList.add(badge);
                }
            }
        }

        notifyDataSetChanged();
    }


    // Method to update the badge list
    public void updateBadgeList(List<Badge> newBadges) {
        badgeList.clear();
        badgeList.addAll(newBadges);

        if (allBadges.isEmpty()){
            allBadges.addAll(newBadges); // Add all badges at the start
        }

        notifyDataSetChanged();
    }

    public static class BadgeViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle;
        ImageView image;

        public BadgeViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_certificate_title);
            subtitle = itemView.findViewById(R.id.text_certificate_subtitle);
            image = itemView.findViewById(R.id.ivBadge);
        }
    }

    public interface OnBadgeClickListener {
        void onBadgeClick(Badge badge);
    }

    private OnBadgeClickListener listener;
    public void setOnBadgeClickListener(OnBadgeClickListener listener) {
        this.listener = listener;
    }

}
