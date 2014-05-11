package br.com.unirio.moodle.activity;

import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.unirio.moodle.Constants;
import br.com.unirio.moodle.R;
import br.com.unirio.moodle.adapter.CoursesAdapter;
import br.com.unirio.moodle.model.CoursesParcel;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class InsideCourseActivity extends ActionBarActivity {

    @InjectView(R.id.listViewCourses)
    public ListView mListView;

    private ListAdapter adapter;

    private List<Resources> resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_course);
        ButterKnife.inject(this);
        resources = new ArrayList<Resources>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inside_course, menu);
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
