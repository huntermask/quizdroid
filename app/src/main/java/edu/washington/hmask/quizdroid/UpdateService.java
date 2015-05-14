package edu.washington.hmask.quizdroid;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

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
                Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
            }
        });

    }
}
