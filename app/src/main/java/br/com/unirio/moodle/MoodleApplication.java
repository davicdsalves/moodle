package br.com.unirio.moodle;

import android.app.Application;

import br.com.unirio.moodle.module.MoodleServiceModule;
import br.com.unirio.moodle.module.RestAdapterModule;
import dagger.ObjectGraph;

/**
 * Created by davi.alves on 02/05/2014.
 */
public class MoodleApplication extends Application {

    private ObjectGraph objectGraph;

    private static MoodleApplication instance;

    public MoodleApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(new RestAdapterModule(), new MoodleServiceModule());
    }

    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }

    public static MoodleApplication getInstance() {
        return instance;
    }
}
