package ph.edu.usc.skillboost.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ph.edu.usc.skillboost.repository.BadgeRepository;
import ph.edu.usc.skillboost.view.adapters.BadgeAdapter;
import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.model.Badge;
import ph.edu.usc.skillboost.viewmodel.BadgeViewModel;
import android.util.Log;

public class BadgesActivity extends BaseActivity {

    private EditText etSearch;
    private RecyclerView rvCertificates;
    private TextView tabYourAwards, tabTopAwards, tabMoreAwards;
    private BadgeViewModel badgeViewModel;
    private BadgeAdapter badgeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_badges);

        initViews();
        setupListeners();

        badgeViewModel = new ViewModelProvider(this).get(BadgeViewModel.class);

        rvCertificates.setLayoutManager(new LinearLayoutManager(this));
        badgeAdapter = new BadgeAdapter(this, new ArrayList<>());
        rvCertificates.setAdapter(badgeAdapter);

        badgeViewModel.getAllBadges().observe(this, this::updateBadgeList);
    }

    private void updateBadgeList(List<Badge> badges) {
        badgeAdapter.updateBadges(badges);
    }


    private List<Badge> getSampleBadges() {
        List<Badge> list = new ArrayList<>();
        list.add(new Badge(1, "Java Basics", "Completed in 2023", "sample_certificate2"));
        list.add(new Badge(2, "Android Advanced", "Completed in 2024", "sample_certificate2"));
        return list;
    }

    private void initViews() {
        etSearch = findViewById(R.id.etSearch);
        rvCertificates = findViewById(R.id.rvCertificates);
        tabYourAwards = findViewById(R.id.tabYourAwards);
        tabTopAwards = findViewById(R.id.tabTopAwards);
        tabMoreAwards = findViewById(R.id.tabMoreAwards);
    }

    private void setupListeners() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("BadgesActivity", "Search Query: " + s.toString());
                badgeAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        tabYourAwards.setOnClickListener(v -> {
            highlightTab(tabYourAwards);
            // Load or filter "Your Awards"
        });

        tabTopAwards.setOnClickListener(v -> {
            highlightTab(tabTopAwards);
            // Load or filter "Top Awards"
        });

        tabMoreAwards.setOnClickListener(v -> {
            highlightTab(tabMoreAwards);
            // Load or filter "More Awards"
        });
    }

    private void highlightTab(TextView selectedTab) {
        tabYourAwards.setTextColor(getResources().getColor(android.R.color.darker_gray));
        tabTopAwards.setTextColor(getResources().getColor(android.R.color.darker_gray));
        tabMoreAwards.setTextColor(getResources().getColor(android.R.color.darker_gray));
        selectedTab.setTextColor(getResources().getColor(android.R.color.white));
    }
}