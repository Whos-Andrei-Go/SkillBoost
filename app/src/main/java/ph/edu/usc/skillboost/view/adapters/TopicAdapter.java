package ph.edu.usc.skillboost.view.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.model.Topic;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private final List<Topic> topicList;
    private final Context context;

    public TopicAdapter(Context context, List<Topic> topicList) {
        this.context = context;
        this.topicList = topicList;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic topic = topicList.get(position);
        holder.topicName.setText(topic.getName());

        // Optional: highlight selected topics
        holder.itemView.setSelected(topic.isSelected());

        // Toggle selection on click
        holder.itemView.setOnClickListener(v -> {
            topic.toggleSelected();
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public static class TopicViewHolder extends RecyclerView.ViewHolder {
        TextView topicName;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            topicName = itemView.findViewById(R.id.topicName);
        }
    }

    public void updateTopics(List<Topic> newTopics) {
        topicList.clear();
        topicList.addAll(newTopics);
        notifyDataSetChanged();
    }

    public List<Topic> getSelectedTopics() {
        List<Topic> selectedTopics = new ArrayList<>();
        for (Topic topic : topicList) {
            if (topic.isSelected()) {
                selectedTopics.add(topic);
            }
        }
        Log.d("PreferencesActivity", "Selected topics: " + selectedTopics.size());
        return selectedTopics;
    }
}


