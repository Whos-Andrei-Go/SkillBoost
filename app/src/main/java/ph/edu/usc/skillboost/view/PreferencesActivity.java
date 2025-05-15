package ph.edu.usc.skillboost.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.model.Topic;
import ph.edu.usc.skillboost.view.adapters.TopicAdapter;
import ph.edu.usc.skillboost.viewmodel.AuthViewModel;

public class PreferencesActivity extends AppCompatActivity {

    private EditText searchBar;
    private Button buttonDone;
    private ImageView backArrow;

    private TopicAdapter topicAdapter;
    private List<Topic> allTopics = new ArrayList<>(); // You'd populate this from a real source
    private List<Topic> filteredTopics = new ArrayList<>();

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        RecyclerView topicRecyclerView = findViewById(R.id.recycler_topics);

        List<Topic> topicList = new ArrayList<>();
        topicList.add(new Topic("Java"));
        topicList.add(new Topic("UI/UX"));
        topicList.add(new Topic("Cybersecurity"));
        topicList.add(new Topic("Project Management"));
        topicList.add(new Topic("Cloud Computing"));
        topicList.add(new Topic("Data Structures"));
        topicList.add(new Topic("Machine Learning"));
        topicList.add(new Topic("Agile Methodology"));
        topicList.add(new Topic("Mobile App Development"));
        topicList.add(new Topic("Software Testing"));

        // Ensure you set the allTopics to topicList
        allTopics.addAll(topicList);

        TopicAdapter adapter = new TopicAdapter(this, topicList);
        topicRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        topicRecyclerView.setAdapter(adapter);

        searchBar = findViewById(R.id.search_bar);
        buttonDone = findViewById(R.id.buttonDone);
        backArrow = findViewById(R.id.backArrow);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Handle search
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTopics(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Done button
        buttonDone.setOnClickListener(v -> {
            // Proceed or save preferences
            List<Topic> selectedTopics = adapter.getSelectedTopics(); // Use adapter here to get the selected topics
            String name = getIntent().getStringExtra("name");
            String email = getIntent().getStringExtra("email");
            String password = getIntent().getStringExtra("password");
            String bio = getIntent().getStringExtra("bio");
            authViewModel.register(email, password, name, bio, selectedTopics);
        });

        backArrow.setOnClickListener(v -> {
            finish();
        });

        authViewModel.getErrorLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null && !errorMessage.isEmpty()) {
                    // Show Toast with error message
                    Toast.makeText(PreferencesActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void filterTopics(String query) {
        filteredTopics.clear();
        for (Topic topic : allTopics) {
            if (topic.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredTopics.add(topic);
            }
        }
        topicAdapter.updateTopics(filteredTopics); // Update the adapter with filtered list
    }

}
