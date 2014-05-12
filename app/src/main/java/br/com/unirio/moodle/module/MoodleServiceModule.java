package br.com.unirio.moodle.module;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;

import java.net.CookieManager;
import java.net.CookiePolicy;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.unirio.moodle.MoodleApplication;
import br.com.unirio.moodle.activity.InsideCourseActivity;
import br.com.unirio.moodle.activity.LoginActivity;
import br.com.unirio.moodle.client.MoodleService;
import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by davi.alves on 10/05/2014.
 */
@Module(injects = {LoginActivity.class, InsideCourseActivity.class}, includes = CookieModule.class)
public class MoodleServiceModule {

    @Inject
    public RestAdapter restAdapter;

    @Provides
    @Singleton
    public MoodleService provideMoodleService() {
        MoodleApplication app = MoodleApplication.getInstance();
        app.getObjectGraph().inject(this);
        return restAdapter.create(MoodleService.class);
    }

    @Provides
    public HtmlCleaner provideHtmlCleaner() {
        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties props = cleaner.getProperties();
        props.setAllowHtmlInsideAttributes(true);
        props.setAllowMultiWordAttributes(true);
        props.setRecognizeUnicodeChars(true);
        props.setOmitComments(true);
        return cleaner;
    }
}
