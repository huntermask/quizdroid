package edu.washington.hmask.quizdroid;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ut91 on 5/21/15.
 */
public class SharedUtilities {

    static final String LOG_TAG = "QuizApp";

    static String inputStreamToString(InputStream is) {
        int size = 0;
        try {
            size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonString = new String(buffer, "UTF-8");
            return jsonString;
        } catch (IOException ex) {
            Log.e(LOG_TAG, "An exception occurred: " + ex.getMessage());
        }
        return null;
    }
}
