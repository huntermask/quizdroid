package edu.washington.hmask.quizdroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huntermask on 5/7/15.
 */
public class TopicRepositoryImpl implements TopicRepository {

    public List<Topic> getTopics() {
        try {
            InputStream is = new FileInputStream("data/questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonString = new String(buffer, "UTF-8");
            JSONArray json = new JSONArray(jsonString);

            List<Topic> topicList = new ArrayList<>();
            for (int i = 0; i < json.length(); i++) {
                // Parse each Topic from the array

                JSONObject topicJson = json.getJSONObject(i);
                Topic t = new Topic(topicJson.getString("title"), topicJson.getString("desc"));
                JSONArray questionsJson = topicJson.getJSONArray("questions");
                List<Question> questionList = new ArrayList<>();
                for (int j = 0; j < questionsJson.length(); j++) {
                    // Parse each Question from the array

                    JSONObject questionJson = questionsJson.getJSONObject(j);
                    List<String> answers = new ArrayList<>();
                    JSONArray answersJson = questionJson.getJSONArray("answers");
                    for (int k = 0; k < answersJson.length(); k++) {
                        // Parse each answer from the array
                        answers.add(answersJson.getString(k));
                    }

                    // Construct the Question from the gathered data
                    Question q = new Question(questionJson.getString("text"), answers, Integer.parseInt(questionJson.getString("answer")) - 1);
                    questionList.add(q);
                }

                t.getQuestions().addAll(questionList);
                topicList.add(t);
            }

            return topicList;
        } catch (IOException | JSONException ex) {
            Log.e("TopicRepositoryImpl", "Unable to load questions file: " + ex.getMessage());
        }

        return null;
    }
}
