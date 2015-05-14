package edu.washington.hmask.quizdroid;

import java.io.InputStream;
import java.util.List;

/**
 * Created by huntermask on 5/7/15.
 */
public interface TopicRepository {

    List<Topic> getTopics();

    void setTopics(List<Topic> topics);

    void setTopics(InputStream is);

}
