package ph.edu.usc.skillboost;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BadgesActivity extends BaseActivity {

    private EditText etSearch;
    private RecyclerView rvCertificates;
    private TextView tabYourAwards, tabTopAwards, tabMoreAwards;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_badges);

        initViews();
        setupListeners();
        rvCertificates.setLayoutManager(new LinearLayoutManager(this));
        rvCertificates.setAdapter(new BadgeAdapter(getSampleBadges()));

    }

    private List<Badge> getSampleBadges() {
        List<Badge> list = new ArrayList<>();
        list.add(new Badge("Java Basics", "Completed in 2023", R.drawable.sample_certificate2));
        list.add(new Badge("Android Advanced", "Completed in 2024", R.drawable.sample_certificate2));
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter your RecyclerView adapter here
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed
            }
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