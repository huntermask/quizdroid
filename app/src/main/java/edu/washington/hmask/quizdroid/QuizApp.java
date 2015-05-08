package edu.washington.hmask.quizdroid;

import android.app.Application;
import android.util.Log;

import java.util.List;

/**
 * Created by huntermask on 5/7/15.
 */
public class QuizApp extends Application {

    private final String LOG_TAG = "QuizApp";

    private TopicRepository topics = new TopicRepositoryImpl();

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
        Log.i(LOG_TAG, "QuizApp constructor fired");
    }

    public List<Topic> getTopics() {
        return topics.getTopics();
    }
}
