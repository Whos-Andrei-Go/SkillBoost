package ph.edu.usc.skillboost.view.adapters;

import android.content.Context;
import android.content.Intent;
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

import ph.edu.usc.skillboost.R;
import ph.edu.usc.skillboost.model.Course;
import ph.edu.usc.skillboost.model.Course;
import ph.edu.usc.skillboost.model.Course;
import ph.edu.usc.skillboost.view.CourseDetailsActivity;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    public enum CardSize {
        SMALL, MEDIUM, LARGE
    }
    private List<Course> courseList;
    private List<Course> fullCourseList;
    private CardSize cardSize;
    private Context context;
    private String source;

    public CourseAdapter(Context context, List<Course> courseList, CardSize cardSize, String source) {
        this.courseList = courseList;
        this.fullCourseList = new ArrayList<>(courseList);
        this.cardSize = cardSize;
        this.context = context;
        this.source = source;
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

//    @Override
//    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
//        Course course = courseList.get(position);
//        holder.title.setText(course.getTitle());
//        holder.description.setText(course.getDescription());
//        holder.image.setImageResource(course.getImageResId());
//    }
    // temporary route
    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);

        // Set course details (title, description, etc.)
        holder.title.setText(course.getTitle());
        holder.subtitle.setText(course.getSubtitle());

        String imageResName = course.getImageUrl(); // Assume this is the name of the image file (e.g., "sample_certificate2")

        int imageResId = context.getResources().getIdentifier(imageResName, "drawable", context.getPackageName());

        if (imageResId == 0) {
            imageResId = R.drawable.course1;
        }

        holder.image.setImageResource(imageResId);

        // Set onClickListener to route to CourseDetailsActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CourseDetailsActivity.class);
            intent.putExtra("source", source); // Pass the origin
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle;
        ImageView image;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_course_title);
            subtitle = itemView.findViewById(R.id.text_course_subtitle);
            image = itemView.findViewById(R.id.image_course);
        }
    }
    private int dpToPx(android.content.Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public void filter(String query) {
        courseList.clear();
        if (query.isEmpty()) {
            courseList.addAll(fullCourseList); // If query is empty, restore the original list
        } else {
            for (Course course : fullCourseList) {
                if (course.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    courseList.add(course);
                }
            }
        }

        notifyDataSetChanged();
    }

    public void updateCourseList(List<Course> newCourses) {
        courseList.clear();
        courseList.addAll(newCourses);

        if (fullCourseList.isEmpty()){
            fullCourseList.addAll(newCourses); // Add all courses at the start
        }

        notifyDataSetChanged();
    }
}

