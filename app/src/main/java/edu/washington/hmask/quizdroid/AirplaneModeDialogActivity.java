package edu.washington.hmask.quizdroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

/**
 * Created by ut91 on 5/21/15.
 */
public class AirplaneModeDialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_alert);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("QuizDroid's questions cannot refresh because you are in airplane mode. " +
                "Please turn off airplane mode in Settings to allow questions to refresh.")
                .setTitle("Airplane Mode On")
                .setPositiveButton(R.string.action_settings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS));
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User selected close
                    }
                })
                .show();
    }
}
