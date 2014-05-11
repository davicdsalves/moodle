package br.com.unirio.moodle.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.unirio.moodle.Constants;
import br.com.unirio.moodle.R;
import br.com.unirio.moodle.adapter.CoursesAdapter;
import br.com.unirio.moodle.model.Course;
import br.com.unirio.moodle.model.CoursesParcel;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class CourseListActivity extends ActionBarActivity {

    @InjectView(R.id.listViewCourses)
    public ListView mListView;

    private ListAdapter adapter;

    private List<Course> courses;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        ButterKnife.inject(this);
        courses = new ArrayList<Course>();

        CoursesParcel parcel = getIntent().getParcelableExtra(Constants.COURSES_KEY);
        if (parcel != null) {
            courses = parcel.getRows();
        }
        adapter = new CoursesAdapter(CourseListActivity.this, courses);
        mListView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
