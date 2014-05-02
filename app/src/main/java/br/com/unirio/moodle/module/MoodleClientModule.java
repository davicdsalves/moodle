package br.com.unirio.moodle.module;

import javax.inject.Singleton;

import br.com.unirio.moodle.activity.LoginActivity;
import br.com.unirio.moodle.client.MoodleClient;
import dagger.Module;
import dagger.Provides;

/**
 * Created by davi.alves on 02/05/2014.
 */
@Module(injects = {LoginActivity.class})
public class MoodleClientModule {

    @Provides @Singleton
    public MoodleClient provideMoodleClient() {
        return new MoodleClient();
    }
}
