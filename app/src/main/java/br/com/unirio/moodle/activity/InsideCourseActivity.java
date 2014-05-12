package br.com.unirio.moodle.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

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
import br.com.unirio.moodle.adapter.ResourcesAdapter;
import br.com.unirio.moodle.client.MoodleService;
import br.com.unirio.moodle.model.FileType;
import br.com.unirio.moodle.model.Resource;
import br.com.unirio.moodle.util.Logger;
import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.client.Response;

public class InsideCourseActivity extends ActionBarActivity {

    @InjectView(R.id.listViewCourseResources)
    public ListView mListView;

    @Inject
    public HtmlCleaner cleaner;

    @Inject
    public MoodleService service;

    private GetCourseInfoTask task;

    private ProgressDialog progressDialog;

    private String courseName;

    private String url;

    private ResourcesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_course);
        ButterKnife.inject(this);
        MoodleApplication app = (MoodleApplication) getApplication();
        app.getObjectGraph().inject(this);

        courseName = getIntent().getStringExtra(Constants.COURSE_NAME_KEY);
        setTitle(courseName);
        url = getIntent().getStringExtra(Constants.COURSE_URL_KEY);

        task = new GetCourseInfoTask();
        task.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (task != null) task.cancel(true);
        if (progressDialog != null) progressDialog.dismiss();
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

    private class GetCourseInfoTask extends AsyncTask<Void, Void, List<Resource>> {

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(InsideCourseActivity.this, getString(R.string.loading_data), getString(R.string.please_wait), true);
        }

        @Override
        protected List<Resource> doInBackground(Void... voids) {
            try {
                int idIndex = url.lastIndexOf(Constants.ID_NAME);
                Long resourceId = Long.valueOf(url.substring(url.indexOf("=", idIndex) + 1));
                return processResponse(service.view(resourceId));
            } catch (Exception e) {
                Logger.e("Erro obtendo informações do curso", e);
            }

            return new ArrayList<Resource>(0);
        }

        @Override
        protected void onPostExecute(List<Resource> resources) {
            if (progressDialog != null) progressDialog.dismiss();

            adapter = new ResourcesAdapter(InsideCourseActivity.this, resources);
            mListView.setAdapter(adapter);
        }


        private List<Resource> processResponse(Response response) throws IOException, XPatherException {
            if (response != null) {
                TagNode tagNode = cleaner.clean(response.getBody().in());
                Object[] resources_nodes = tagNode.evaluateXPath(Constants.RESOURCE_URL_XPATH);

                if (resources_nodes.length > 0) {
                    TagNode resourcesNode = (TagNode) resources_nodes[0];

                    List<TagNode> childs = resourcesNode.getChildTagList();
                    List<Resource> resources = new ArrayList<Resource>(childs.size());

                    for (TagNode child : childs) {

                        ///TODO - IF para pegar a string depois da 'ultima / e antes antes do .XXX! no TYPE_XPATH

                        Object[] resourceNameNode = child.evaluateXPath(Constants.RESOURCE_NAME_XPATH);
                        Object[] resourceUrlNode = child.evaluateXPath(Constants.RESOURCE_URL_XPATH);
                        Object[] resourceTypeNode = child.evaluateXPath(Constants.RESOURCE_TYPE_XPATH);


                        if (resourceNameNode.length > 0) {

                            StringBuilder builderResourceName = (StringBuilder) resourceNameNode[0];
                            StringBuilder builderResourceUrl = (StringBuilder) resourceUrlNode[0];
                            String builderResourceType = (String) resourceTypeNode[0];

                            builderResourceType.replace("http://uniriodb2.uniriotec.br/pix/f/", "");
                            builderResourceType.replace(".gif", "");

                            String resourceName = builderResourceName.toString();
                            String resourceType = builderResourceType;
                            String resourceUrl = builderResourceUrl.toString();

                            Resource resource = new Resource(resourceName, resourceUrl, FileType.valueOf(resourceType));
                            resources.add(resource);

                            Logger.d("resourceName[%s]", resourceName);
                        }
                    }
                    return resources;
                }
            }
            return new ArrayList<Resource>(0);
        }

    }

}
