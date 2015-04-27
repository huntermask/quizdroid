package edu.washington.hmask.quizdroid.dummy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.washington.hmask.quizdroid.Question;
import edu.washington.hmask.quizdroid.Topic;

/**
 * Created by ut91 on 4/23/15.
 */
public class QuizContent {

    public static List<Topic> topics = new ArrayList<>();
    public static Map<String, Topic> topicMap = new HashMap();

    static {
        Topic mathTopic = new Topic("Math", "The study of numbers.");
        mathTopic.getQuestions().add(new Question("What is 2 + 2?", new ArrayList<>(Arrays.asList(new String[] {"1", "2", "3", "4"})), 1));
        mathTopic.getQuestions().add(new Question("What is 3 + 3?", new ArrayList<>(Arrays.asList(new String [] {"3", "4", "5", "6"})), 3));
        addItem("1", mathTopic);
        addItem("2", new Topic("Physics", "The study of the known universe."));
        addItem("3", new Topic("Marvel Super Heroes", "Main characters of Marvel graphic novels."));
    }

    private static void addItem(String index, Topic t) {
        topicMap.put(index, t);
        topics.add(t);
    }

}
