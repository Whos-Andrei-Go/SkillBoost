package ph.edu.usc.skillboost.view.adapters;

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

    private List<Course> courseList;

    public CourseAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
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
}

