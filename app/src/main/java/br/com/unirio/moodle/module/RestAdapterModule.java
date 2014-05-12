package br.com.unirio.moodle.module;

import com.squareup.okhttp.OkHttpClient;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.unirio.moodle.Constants;
import br.com.unirio.moodle.MoodleApplication;
import br.com.unirio.moodle.util.Logger;
import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by davi.alves on 10/05/2014.
 */
@Module(injects = MoodleServiceModule.class)
public class RestAdapterModule {

    @Inject
    public CookieManager cookieManager;

    @Provides
    @Singleton
    public RestAdapter provideRestAdapter() {
        MoodleApplication app = MoodleApplication.getInstance();
        app.getObjectGraph().inject(this);

        OkHttpClient httpClient = new OkHttpClient();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
        return new RestAdapter.Builder()
                .setEndpoint(Constants.API_URL)
                .setLogLevel(RestAdapter.LogLevel.HEADERS)
                .setLog(new Logger())
                .setClient(new OkClient(httpClient))
                .build();
    }
}
