package ph.edu.usc.skillboost.view;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ph.edu.usc.skillboost.model.Badge;
import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.model.Course;
import ph.edu.usc.skillboost.utils.Utilities;
import ph.edu.usc.skillboost.view.adapters.BadgeAdapter;
import ph.edu.usc.skillboost.view.adapters.FilterAdapter;
import ph.edu.usc.skillboost.viewmodel.BadgeViewModel;
import ph.edu.usc.skillboost.viewmodel.CourseViewModel;

public class BadgesActivity extends BaseActivity {

    private EditText searchBar;
    private RecyclerView badgeRecycler, filterRecycler;
    private BadgeAdapter badgeAdapter;
    private FilterAdapter filterAdapter;
    private ImageView backBtn, bookmarkedBtn;
    private BadgeViewModel badgeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_badges);

        initViews();
        setupRecyclerViews();
        setupSearch();

        backBtn.setOnClickListener(v -> finish());

        badgeAdapter.setOnBadgeClickListener(badge -> showBadgeDialog(badge));

    }

    private void initViews() {
        searchBar = findViewById(R.id.search_bar);
        badgeRecycler = findViewById(R.id.badgeRecycler);
        filterRecycler = findViewById(R.id.filterRecycler);
        backBtn = findViewById(R.id.back);
    }

    private void setupRecyclerViews() {
        badgeViewModel = new ViewModelProvider(this).get(BadgeViewModel.class);

        // Set up RecyclerView for badges
        badgeRecycler.setLayoutManager(new LinearLayoutManager(this));
        badgeAdapter = new BadgeAdapter(this, new ArrayList<>(), BadgeAdapter.CardSize.LARGE);
        badgeRecycler.setAdapter(badgeAdapter);

        badgeViewModel.getAllBadges().observe(this, this::updateBadgeList);

        // Set up RecyclerView for filters (horizontal)
        filterRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        filterAdapter = new FilterAdapter(getFilterList(), filter -> {
            badgeAdapter.filterByCategory(filter);
        });
        filterRecycler.setAdapter(filterAdapter);
    }

    private void setupSearch() {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                badgeAdapter.filter(s.toString());
            }
        });
    }

    private List<String> getFilterList() {
        List<String> filters = new ArrayList<>();
        filters.add("Top Awards");
        filters.add("Your Awards");
        filters.add("More Awards");
        return filters;
    }

    private void updateBadgeList(List<Badge> badges) {
        badgeAdapter.updateBadgeList(badges);
    }

    private void showBadgeDialog(Badge badge) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogue_badge_details);

        ImageView imageView = dialog.findViewById(R.id.dialog_badge_image);
        TextView title = dialog.findViewById(R.id.dialog_badge_title);
        TextView subtitle = dialog.findViewById(R.id.dialog_badge_subtitle);
        TextView closeBtn = dialog.findViewById(R.id.dialog_close);

        imageView.setImageResource(Utilities.getDrawableFromRes(this, badge.getImageRes()));
        title.setText(badge.getTitle());
        subtitle.setText(badge.getSubtitle());

        closeBtn.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }


//    private List<Badge> getSampleBadges() {
//        List<Badge> list = new ArrayList<>();
//        list.add(new Badge("1", "Java Basics", "Completed in 2023", "sample_certificate_2.png", Arrays.asList("Your Awards")));
//        list.add(new Badge("2", "Android Advanced", "Completed in 2024", "sample_certificate_2.png", Arrays.asList("Top Awards")));
//        list.add(new Badge("3", "Kotlin Mastery", "Completed in 2025", "sample_certificate_2.png", Arrays.asList("More Awards")));
//        return list;
//    }
}
