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

        /* private List<Resource> processResponse(Response response) throws IOException, XPatherException {

            tagNode = cleaner.clean(response.getBody().in());
            Object[] resources_nodes = tagNode.evaluateXPath(Constants.RESOURCE_URL_XPATH);

            if (courses_nodes.length > 0) {
                TagNode tagNode = (TagNode) courses_nodes[0];
                List<TagNode> childs = tagNode.getChildTagList();
                List<Course> courses = new ArrayList<Course>(childs.size());
                for (TagNode child : childs) {
                    Object[] courseNameNode = child.evaluateXPath(Constants.COURSE_NAME_XPATH);
                    Object[] teacherNameNode = child.evaluateXPath(Constants.TEACHER_NAME_XPATH);
                    Object[] courseUrlNode = child.evaluateXPath(Constants.COURSE_URL_XPATH);
                    if (courseNameNode.length > 0 && teacherNameNode.length > 0) {
                        StringBuilder builderCourse = (StringBuilder) courseNameNode[0];
                        StringBuilder builderTeacher = (StringBuilder) teacherNameNode[0];
                        String courseUrl = (String) courseUrlNode[0];
                        String courseName = builderCourse.toString();
                        String teacherName = builderTeacher.toString();
                        Course course = new Course(courseName, teacherName, courseUrl);
                        courses.add(course);
                        Logger.d("courseName[%s], teacherName[%s], url[%s]", courseName, teacherName, courseUrl);
                    }
                }
                return courses;
            }
            return new ArrayList<Course>(0);
        } */

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
