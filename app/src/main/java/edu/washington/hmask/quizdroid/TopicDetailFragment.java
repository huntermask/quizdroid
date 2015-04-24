package edu.washington.hmask.quizdroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import edu.washington.hmask.quizdroid.dummy.DummyContent;
import edu.washington.hmask.quizdroid.dummy.QuizContent;

/**
 * A fragment representing a single Topic detail screen.
 * This fragment is either contained in a {@link TopicListActivity}
 * in two-pane mode (on tablets) or a {@link TopicDetailActivity}
 * on handsets.
 */
public class TopicDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Topic mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TopicDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = QuizContent.topicMap.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View detailView = inflater.inflate(R.layout.fragment_topic_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            TextView topicLabel = (TextView) detailView.findViewById(R.id.topicLabel);
            topicLabel.setText(mItem.getName());

            TextView descriptionLabel = (TextView) detailView.findViewById(R.id.descriptionText);
            descriptionLabel.setText(mItem.getDescription());

            TextView questionCountLabel = (TextView) detailView.findViewById(R.id.numberQuestionsLabel);
            questionCountLabel.setText(Integer.toString(mItem.getQuestions().size()) + (mItem.getQuestions().size() == 1 ? " question" : " questions"));

            Button beginTopicButton = (Button) detailView.findViewById(R.id.beginTopicButton);
            if (mItem.getQuestions().size() == 0) {
                beginTopicButton.setEnabled(false);
            }

        }

        return detailView;
    }
}
