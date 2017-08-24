package com.binay.globalhuntapplication;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;


import java.util.List;

/**
 * Created by Binay.
 */

public class MainApplication extends Application {

    private NetComponent mNetComponent;
    public static MainApplication mainApplicationInstance;
    private boolean isNoInternetDialogVisible = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mainApplicationInstance = this;
         /*
            Dagger init with app and net module to connect and intialize for network procedure call // Base URL can be in BuildConfig file for keeping constance
         */
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .netModule(new NetModule("http://a2b7cf8676394fda75de-6e0550a16cd96615f7274fd70fa77109.r93.cf3.rackcdn.com/common/json/", this))
                .build();
    }

    /*
    * Function to return main application instance*/
    public static MainApplication getInstance() {
        return mainApplicationInstance;
    }

    /*
    * Method to check is Activity is in foreground that helps to make network data call
    * */
    public boolean isAppForeGround() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = getApplicationContext().getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /*
    * Initialization fo net component for accessing and moving before api call*/
    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    /*Methods for internet availability check dialog*/
    public void setNoInternetDialogVisibility(boolean isVisible) {
        this.isNoInternetDialogVisible = isVisible;
    }


    public boolean getNoInternetDialogVisibility() {
        return isNoInternetDialogVisible;
    }

}
