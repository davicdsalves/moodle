package br.com.unirio.moodle.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.Toast;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.unirio.moodle.Constants;
import br.com.unirio.moodle.MoodleApplication;
import br.com.unirio.moodle.R;
import br.com.unirio.moodle.client.MoodleService;
import br.com.unirio.moodle.model.Course;
import br.com.unirio.moodle.model.CoursesParcel;
import br.com.unirio.moodle.model.LoginResponse;
import br.com.unirio.moodle.service.SessionService;
import br.com.unirio.moodle.util.Logger;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.client.Response;

public class LoginActivity extends Activity {

    @InjectView(R.id.editTextUsername)
    public EditText mEditTextUsername;

    @InjectView(R.id.editTextPassword)
    public EditText mEditTextPassword;

    @Inject
    public MoodleService service;

    @Inject
    public HtmlCleaner cleaner;

    private String mUsername;
    private String mPassword;

    private ProgressDialog progressDialog;

    private AuthenticateTask task;

    private SessionService sessionService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        MoodleApplication app = (MoodleApplication) getApplication();
        app.getObjectGraph().inject(this);

        sessionService = new SessionService(this);
        mEditTextUsername.setText(sessionService.getKeyUserName());
    }

    @OnClick(R.id.buttonLogin)
    public void onLoginClick() {
        Editable emailEditable = mEditTextUsername.getEditableText();
        Editable passwordEditable = mEditTextPassword.getEditableText();
        if (emailEditable != null && passwordEditable != null) {
            mUsername = emailEditable.toString();
            mPassword = passwordEditable.toString();
            task = new AuthenticateTask();
            task.execute();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (task != null) task.cancel(true);
        if (progressDialog != null) progressDialog.dismiss();
    }

    private class AuthenticateTask extends AsyncTask<Void, Void, LoginResponse> {

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LoginActivity.this, getString(R.string.sending_data), getString(R.string.please_wait), true);

        }

        @Override
        protected LoginResponse doInBackground(Void... voids) {
            try {
                service.accessPage();
                Response response = service.login(mUsername, mPassword);
                String url = response.getUrl();
                Logger.i("Request sent, email[%s], pass[%s], status[%d], url[%s]", mUsername, mPassword, response.getStatus(), url);
                if (isRedirectToHome(url)) {
                    sessionService.insertLogin(mUsername);
                    List<Course> courses = (processResponse(response));
                    return new LoginResponse(true, courses);
                }
            } catch (Exception e) {
                Logger.e("Erro enviando request de login", e);
            }
            return new LoginResponse(false, new ArrayList<Course>(0));
        }

        private List<Course> processResponse(Response response) throws IOException, XPatherException {
            TagNode tagNode = cleaner.clean(response.getBody().in());
            Object[] courses_nodes = tagNode.evaluateXPath(Constants.COURSES_XPATH);
            if (courses_nodes.length > 0) {
                TagNode coursesNode = (TagNode) courses_nodes[0];
                List<TagNode> childs = coursesNode.getChildTagList();
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
        }

        @Override
        protected void onPostExecute(LoginResponse result) {
            if (progressDialog != null) progressDialog.dismiss();

            if (!result.isLogged()) {
                Toast.makeText(LoginActivity.this, getString(R.string.error_login), Toast.LENGTH_LONG).show();
            } else {
                CoursesParcel parcel = new CoursesParcel(result.getCourses());
                Intent intent = new Intent(LoginActivity.this, CourseListActivity.class);
                intent.putExtra(Constants.COURSES_KEY, parcel);
                startActivity(intent);
                finish();
            }
        }

        private boolean isRedirectToHome(String url) {
            return Constants.API_URL.equalsIgnoreCase(url);
        }
    }
}
