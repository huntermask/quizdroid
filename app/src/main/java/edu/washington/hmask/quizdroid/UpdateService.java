package edu.washington.hmask.quizdroid;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import java.io.File;

/**
 * Created by huntermask on 5/13/15.
 */
public class UpdateService extends IntentService {

    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String url = intent.getStringExtra("url");

        Handler h = new Handler(getApplicationContext().getMainLooper());

        h.post(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager cm =
                        (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                boolean isAirplaneMode = activeNetwork == null;

                if (isConnected) {
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
                } else if (isAirplaneMode) {
                    Intent i = new Intent(getApplicationContext(), AirplaneModeDialogActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to refresh questions", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
