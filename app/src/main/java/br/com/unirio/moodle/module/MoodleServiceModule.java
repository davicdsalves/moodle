package br.com.unirio.moodle.module;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.unirio.moodle.MoodleApplication;
import br.com.unirio.moodle.activity.LoginActivity;
import br.com.unirio.moodle.client.MoodleService;
import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by davi.alves on 10/05/2014.
 */
@Module(injects = {LoginActivity.class})
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
}
