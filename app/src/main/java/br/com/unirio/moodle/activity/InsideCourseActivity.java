package br.com.unirio.moodle.activity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.common.io.ByteStreams;

import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.unirio.moodle.Constants;
import br.com.unirio.moodle.MoodleApplication;
import br.com.unirio.moodle.R;
import br.com.unirio.moodle.StringUtil;
import br.com.unirio.moodle.adapter.ResourcesAdapter;
import br.com.unirio.moodle.client.MoodleService;
import br.com.unirio.moodle.model.Resource;
import br.com.unirio.moodle.model.ZipDownload;
import br.com.unirio.moodle.util.Logger;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import retrofit.client.Response;

public class InsideCourseActivity extends ActionBarActivity {

    @InjectView(R.id.listViewCourseResources)
    public ListView mListView;

    @Inject
    public HtmlCleaner cleaner;

    @Inject
    public MoodleService service;

    private GetCourseInfoTask task;

    private GetFileTask fileTask;

    private ProgressDialog progressDialog;

    private String courseName;

    private String url;

    private ResourcesAdapter adapter;

    private DownloadManager dm;

    private long downloadID;

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
        if (fileTask != null) fileTask.cancel(true);
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
        switch (id) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_logout:
                Intent i = new Intent(this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnItemClick(R.id.listViewCourseResources)
    public void getFile(int position) {
        Resource resource = adapter.getItem(position);
        String fileUrl = resource.getUrl();
        String fileName = resource.getName();
        fileTask = new GetFileTask(fileUrl, fileName);
        fileTask.execute();
    }

    private class GetFileTask extends AsyncTask<Void, Void, Boolean> {

        private String fileUrl;
        private String fileTitle;

        private GetFileTask(String fileUrl, String fileTitle) {
            this.fileUrl = fileUrl;
            this.fileTitle = fileTitle;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(InsideCourseActivity.this, getString(R.string.downloading_file), getString(R.string.please_wait), true);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Long resourceId = StringUtil.getIdFromUrl(fileUrl);
                Response response = service.getFile(resourceId);
                String downloadUrl = response.getUrl();
                String fileName = StringUtil.getFileName(downloadUrl);

                Logger.i("Request sent, status[%d], url[%s]", response.getStatus(), downloadUrl);

                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdirs();

                //Redirecionado pra pagina de zips
                if (downloadUrl.contains(Constants.VIEW_NAME)) {
                    ZipDownload zipDownload = processResponse(response);
                    if (zipDownload != null) {
                        if (zipDownload.getFolder() == null)
                            response = service.download(zipDownload.getId(), zipDownload.getName());
                        else
                            response = service.download(zipDownload.getId(), zipDownload.getFolder(), zipDownload.getName());
                        fileName = StringUtil.getFileName(zipDownload.getName());
                        Logger.i("Request sent, status[%d], fileName[%s]", response.getStatus(), fileName);
                    }
                }
                return writeFile(response, fileName);
            } catch (Exception e) {
                Logger.e("Erro ao baixar arquivo", e);
            }
            return false;
        }

        private ZipDownload processResponse(Response response) throws IOException, XPatherException {
            if (response != null) {

                TagNode tagNode = cleaner.clean(response.getBody().in());
                Object[] zipNode = tagNode.evaluateXPath(Constants.ZIP_URL_XPATH);
                if (zipNode.length > 0) {
                    String link = (String) zipNode[0];
                    return createZipDownload(link);
                }
            }
            return null;
        }

        private ZipDownload createZipDownload(String link) {
            String downloadLink = StringUtil.getDownloadLink(link);
            int firstSlash = downloadLink.indexOf('/');
            int lastSlash = downloadLink.lastIndexOf('/');
            Long id = Long.valueOf(downloadLink.substring(0, firstSlash));
            String name = downloadLink.substring(lastSlash + 1);
            if (lastSlash == firstSlash) {
                return new ZipDownload(id, name);
            }
            String folder = downloadLink.substring(firstSlash + 1, lastSlash);
            return new ZipDownload(folder, id, name);
        }

        private boolean writeFile(Response response, String fileName) {
            File file = new File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

            FileOutputStream output = null;
            try {
                try {
                    output = new FileOutputStream(file);
                    ByteStreams.copy(response.getBody().in(), output);
                    return true;
                } finally {
                    if (output != null) {
                        output.flush();
                        output.close();
                    }
                }
            } catch (Exception e) {
                Logger.e("Erro ao baixar arquivo", e);
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (progressDialog != null) progressDialog.dismiss();
            if (result)
                Toast.makeText(InsideCourseActivity.this, "Arquivo salvo com sucesso na pasta Downloads.", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(InsideCourseActivity.this, "Erro ao baixar arquivo, tente novamente.", Toast.LENGTH_LONG).show();
        }
    }

    private class GetCourseInfoTask extends AsyncTask<Void, Void, List<Resource>> {

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(InsideCourseActivity.this, getString(R.string.loading_data), getString(R.string.please_wait), true);
        }

        @Override
        protected List<Resource> doInBackground(Void... voids) {
            try {
                Long resourceId = StringUtil.getIdFromUrl(url);
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
                Object[] resources_nodes = tagNode.evaluateXPath(Constants.RESOURCES_XPATH);

                if (resources_nodes.length > 0) {
                    List<Resource> resources = new ArrayList<Resource>(resources_nodes.length);
                    for (Object resourceNode : resources_nodes) {

                        TagNode resourcesNode = (TagNode) resourceNode;

                        Object[] resourceNameNode = resourcesNode.evaluateXPath(Constants.RESOURCE_NAME_XPATH);
                        Object[] resourceUrlNode = resourcesNode.evaluateXPath(Constants.RESOURCE_URL_XPATH);
                        Object[] resourceTypeNode = resourcesNode.evaluateXPath(Constants.RESOURCE_TYPE_XPATH);

                        if (resourceNameNode.length > 0) {
                            TagNode span = (TagNode) resourceNameNode[0];
                            ContentNode nameNode = (ContentNode) span.getAllChildren().get(0);

                            String resourceName = nameNode.getContent();
                            String resourceUrl = (String) resourceUrlNode[0];
                            String builderResourceType = (String) resourceTypeNode[0];

                            String resourceType = builderResourceType.replace(Constants.ATT_URL, Constants.EMPTY).replace(Constants.GIF, Constants.EMPTY);

                            Resource resource = new Resource(resourceName, resourceUrl, resourceType.toUpperCase());
                            resources.add(resource);

                            Logger.d("resourceName[%s], resourceType[%s], resourceUrl[%s]", resourceName, resourceType, resourceUrl);
                        }
                    }
                    return resources;
                }
            }
            return new ArrayList<Resource>(0);
        }

    }

}

