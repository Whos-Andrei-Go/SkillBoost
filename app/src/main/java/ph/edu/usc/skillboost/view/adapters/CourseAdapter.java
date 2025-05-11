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
import ph.edu.usc.skillboost.model.Course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    public enum CardSize {
        SMALL, MEDIUM, LARGE
    }
    private List<Course> courseList;
    private CardSize cardSize;
    private Context context;

    public CourseAdapter(Context context, List<Course> courseList, CardSize cardSize) {
        this.courseList = courseList;
        this.cardSize = cardSize;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        // Set card size dynamically
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;

        switch (cardSize) {
            case SMALL:
                width = dpToPx(parent.getContext(), 160);
                height = dpToPx(parent.getContext(), 220);
                break;
            case MEDIUM:
                width = dpToPx(parent.getContext(), 230);
                height = dpToPx(parent.getContext(), 300);
                break;
            case LARGE:
                width = dpToPx(parent.getContext(), 340);
                height = dpToPx(parent.getContext(), 280);
                break;
        }

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(width, height);
        layoutParams.setMargins(dpToPx(context, 0), dpToPx(context, 0), dpToPx(context, 30), dpToPx(context, 30));
        view.setLayoutParams(layoutParams);


        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.title.setText(course.getTitle());
        holder.description.setText(course.getDescription());
        holder.image.setImageResource(course.getImageResId());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView image;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_course_title);
            description = itemView.findViewById(R.id.text_course_desc);
            image = itemView.findViewById(R.id.image_course);
        }
    }
    private int dpToPx(android.content.Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public void updateCourseList(List<Course> filteredCourses) {
        this.courseList = filteredCourses;
        notifyDataSetChanged();
    }
}

