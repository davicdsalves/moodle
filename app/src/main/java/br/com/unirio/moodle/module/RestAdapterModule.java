package br.com.unirio.moodle.module;

import javax.inject.Singleton;

import br.com.unirio.moodle.Constants;
import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by davi.alves on 10/05/2014.
 */
@Module(injects = MoodleServiceModule.class)
public class RestAdapterModule {

    @Provides
    @Singleton
    public RestAdapter provideRestAdapter() {
        return new RestAdapter.Builder()
                .setEndpoint(Constants.API_URL)
                .build();
    }
}
