package br.com.unirio.moodle;

import android.app.Application;

import br.com.unirio.moodle.module.MoodleClientModule;
import dagger.ObjectGraph;

/**
 * Created by davi.alves on 02/05/2014.
 */
public class MoodleApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(new MoodleClientModule());
    }

    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }

}
