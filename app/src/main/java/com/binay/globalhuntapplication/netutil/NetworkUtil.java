package com.binay.globalhuntapplication.netutil;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.binay.globalhuntapplication.MainApplication;
import com.binay.globalhuntapplication.R;

/**
 * Created by Binay.
 */

public class NetworkUtil {
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
    public static void showNoInternetAlertDialog(Activity activity) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity, R.style.MyAlertDialogStyle);

        builder.setTitle("No internet connection");
        builder.setMessage("There seems to be no internet\nconnection. Check your\nconnection and try again.");

        builder.setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                dialog.dismiss();
                MainApplication.getInstance().setNoInternetDialogVisibility(false);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MainApplication.getInstance().setNoInternetDialogVisibility(false);
            }
        });

        final android.support.v7.app.AlertDialog dialog;

        dialog = builder.create();
        MainApplication.getInstance().setNoInternetDialogVisibility(true);

        dialog.setCancelable(true);
        dialog.show();

    }
}
