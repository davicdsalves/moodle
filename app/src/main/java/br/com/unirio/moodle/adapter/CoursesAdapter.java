package br.com.unirio.moodle.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.unirio.moodle.R;
import br.com.unirio.moodle.model.Course;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by davi.alves on 11/05/2014.
 */
public class CoursesAdapter extends ListAdapter<Course> {


    public CoursesAdapter(Activity activity, List<Course> courses) {
        super(activity, courses);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Course courseRow = list.get(position);
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.courses_row, parent, false);
            holder = new ViewHolder(view);
            if (view != null) {
                view.setTag(holder);
            }
        }

        holder.courseName.setText(courseRow.getName());
        holder.teacherName.setText(courseRow.getTeacher());

        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.textViewCourseName)
        TextView courseName;
        @InjectView(R.id.textViewTeacherName)
        TextView teacherName;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
