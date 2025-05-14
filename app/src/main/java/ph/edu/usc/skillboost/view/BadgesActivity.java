package ph.edu.usc.skillboost.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ph.edu.usc.skillboost.model.Badge;
import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.view.adapters.BadgeAdapter;
import ph.edu.usc.skillboost.view.adapters.FilterAdapter;

public class BadgesActivity extends BaseActivity {

    private EditText searchBar;
    private RecyclerView badgeRecycler, filterRecycler;
    private BadgeAdapter badgeAdapter;
    private FilterAdapter filterAdapter;
    private ImageView backBtn, bookmarkedBtn;
    private List<Badge> allBadges;
    private List<Badge> filteredBadges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_badges);

        initViews();
        setupRecyclerViews();
        setupSearch();
        setupFilter();

        backBtn.setOnClickListener(v -> finish());
        bookmarkedBtn.setOnClickListener(v -> {
            // TODO: Navigate to bookmarked badges
        });
    }

    private void initViews() {
        searchBar = findViewById(R.id.search_bar);
        badgeRecycler = findViewById(R.id.recycler_view_badges);
        filterRecycler = findViewById(R.id.filterRecycler);
        backBtn = findViewById(R.id.back);
        bookmarkedBtn = findViewById(R.id.bookmarked);
    }

    private void setupRecyclerViews() {
        // Set up RecyclerView for badges
        badgeRecycler.setLayoutManager(new LinearLayoutManager(this));
        allBadges = getSampleBadges();  // Get all available badges
        filteredBadges = new ArrayList<>(allBadges); // Initialize filtered badges with all badges initially
        badgeAdapter = new BadgeAdapter(this, filteredBadges);
        badgeRecycler.setAdapter(badgeAdapter);

        // Set up RecyclerView for filters (horizontal)
        filterRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        filterAdapter = new FilterAdapter(getFilterList(), filter -> {
            Toast.makeText(this, "Selected: " + filter, Toast.LENGTH_SHORT).show();
            applyFilter(filter);  // Apply selected filter to badges
        });
        filterRecycler.setAdapter(filterAdapter);
    }

    private void setupSearch() {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                //badgeAdapter.filter(s.toString());
            }
        });
    }

    private void setupFilter() {
        // Filters are already handled by the FilterAdapter in the horizontal RecyclerView
    }

    private List<String> getFilterList() {
        List<String> filters = new ArrayList<>();
        filters.add("Your Awards");
        filters.add("Top Awards");
        filters.add("More Awards");
        return filters;
    }

    private void applyFilter(String filter) {
        List<Badge> filteredList = new ArrayList<>();

        // Apply different filter logic based on the selected filter option
        switch (filter) {
            case "Your Awards":
                // Example filter: Show badges earned by the user (this logic should be customized)
                filteredList.addAll(filterYourAwards());
                break;
            case "Top Awards":
                // Example filter: Show only "Top" badges (custom logic needed)
                filteredList.addAll(filterTopAwards());
                break;
            case "More Awards":
                // Example filter: Show more awards (custom logic needed)
                filteredList.addAll(filterMoreAwards());
                break;
            default:
                filteredList.addAll(allBadges);  // Default is to show all badges
        }

        filteredBadges = filteredList;  // Update the filtered list
        badgeAdapter.updateBadgeList(filteredBadges);  // Update the adapter with new filtered badges
    }

    private List<Badge> filterYourAwards() {
        // Custom logic to filter "Your Awards" based on user criteria
        // Example: returning all badges in this case
        return allBadges;
    }

    private List<Badge> filterTopAwards() {
        // Custom logic to filter "Top Awards"
        // Example: returning all badges in this case
        return allBadges;
    }

    private List<Badge> filterMoreAwards() {
        // Custom logic to filter "More Awards"
        // Example: returning all badges in this case
        return allBadges;
    }

    private List<Badge> getSampleBadges() {
        List<Badge> list = new ArrayList<>();
        list.add(new Badge("1", "Java Basics", "Completed in 2023", "sample_certificate2", Arrays.asList("Your Awards")));
        list.add(new Badge("2", "Android Advanced", "Completed in 2024", "sample_certificate2", Arrays.asList("Top Awards")));
        list.add(new Badge("3", "Kotlin Mastery", "Completed in 2025", "sample_certificate2", Arrays.asList("More Awards")));
        return list;
    }
}
