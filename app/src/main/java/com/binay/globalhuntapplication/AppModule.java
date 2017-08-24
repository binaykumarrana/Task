package com.binay.globalhuntapplication;

import android.app.Application;

import com.binay.globalhuntapplication.MainApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Binay.
 */

@Module
public class AppModule {
    /*
    * Main application instance*/
    MainApplication mApplication;

    public AppModule(MainApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

}
