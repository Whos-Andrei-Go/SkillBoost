package ph.edu.usc.skillboost.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ph.edu.usc.skillboost.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final List<String> notifications;
    private final Context context;

    public NotificationAdapter(Context context, List<String> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textNotification.setText(notifications.get(position));
        holder.icon.setImageResource(R.drawable.star);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNotification;
        ImageView icon;

        ViewHolder(View itemView) {
            super(itemView);
            textNotification = itemView.findViewById(R.id.text_notification);
            icon = itemView.findViewById(R.id.icon_notification);
        }
    }
}
