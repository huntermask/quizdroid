package edu.washington.hmask.quizdroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by ut91 on 5/21/15.
 */
public class DownloadFailedDialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_alert);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("QuizDroid was unable to refresh the list of topics and questions. ")
                .setTitle("Unable to Refresh Questions")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(getIntent().getStringExtra("url")));
                        dm.enqueue(request);
                        finish();
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .show();
    }
}
