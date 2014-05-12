package br.com.unirio.moodle.module;

import java.net.CookieManager;
import java.net.CookiePolicy;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by davi.alves on 12/05/2014.
 */
@Module(injects = RestAdapterModule.class, complete = false, library = true)
public class CookieModule {

    @Provides
    @Singleton
    public CookieManager provideCookieManager() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        return cookieManager;
    }
}
