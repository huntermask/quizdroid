package edu.washington.hmask.quizdroid;

import android.app.Application;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by huntermask on 5/7/15.
 */
public class QuizApp extends Application {

    TopicRepository topics = new TopicRepositoryImpl();

    private static QuizApp ourInstance = null;

    public QuizApp() {
        if (ourInstance == null) {
            ourInstance = this;
        } else {
            throw new RuntimeException("Cannot create more than one instance");
        }
    }

    @Override
    public void onCreate() {
        Log.i(SharedUtilities.LOG_TAG, "QuizApp constructor fired");
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE); // Add more filters here that you want the receiver to listen to
            registerReceiver(receiver, filter);
            try {
                topics = new TopicRepositoryImpl(openFileInput("questions.json"));
            } catch (FileNotFoundException e) {
                topics = new TopicRepositoryImpl(getAssets().open("questions.json"));
            }
        } catch (IOException ex) {
            Log.i(SharedUtilities.LOG_TAG, "An exception occurred: " + ex.getMessage());
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            DownloadManager dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);

            Log.i("MyApp BroadcastReceiver", "onReceive of registered download reciever");

            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long downloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);

                // if the downloadID exists
                if (downloadID != 0) {

                    // Check status
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadID);
                    Cursor c = dm.query(query);
                    if(c.moveToFirst()) {
                        int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        switch(status) {
                            case DownloadManager.STATUS_PAUSED:
                            case DownloadManager.STATUS_PENDING:
                            case DownloadManager.STATUS_RUNNING:
                                break;
                            case DownloadManager.STATUS_SUCCESSFUL:
                                // The download-complete message said the download was "successful" then run this code
                                ParcelFileDescriptor file;
                                try {
                                    // Get file from Download Manager (which is a system service as explained in the onCreate)
                                    file = dm.openDownloadedFile(downloadID);
                                    FileInputStream fis = new FileInputStream(file.getFileDescriptor());
                                    String fileString = SharedUtilities.inputStreamToString(fis);
                                    try {
                                        File f = new File(getFilesDir().getAbsolutePath(), "questions.json");
                                        FileOutputStream fos = new FileOutputStream(f);
                                        fos.write(fileString.getBytes());
                                        fos.close();
                                        topics = new TopicRepositoryImpl(openFileInput("questions.json"));
                                    }
                                    catch (IOException e) {
                                        Log.e(SharedUtilities.LOG_TAG, "File write failed: " + e.toString());
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case DownloadManager.STATUS_FAILED:
                                // YOUR CODE HERE! Your download has failed! Now what do you want it to do? Retry? Quit application? up to you!
                                break;
                        }
                    }
                }
            }
        }
    };

    public List<Topic> getTopics() {
        return topics.getTopics();
    }
}
