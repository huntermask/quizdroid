package edu.washington.hmask.quizdroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A fragment representing a single Topic detail screen.
 * This fragment is either contained in a {@link TopicListActivity}
 * in two-pane mode (on tablets) or a {@link QuizActivity}
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
        if (null != savedInstanceState) {
            mItem = savedInstanceState.getParcelable("topic");
        } else if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = ((QuizApp) getActivity().getApplication()).getTopics().get(Integer.parseInt(getArguments().getString(ARG_ITEM_ID)));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mItem = ((QuizApp) getActivity().getApplication()).getTopics().get(Integer.parseInt(getArguments().getString(ARG_ITEM_ID)));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable("topic", mItem);

        super.onSaveInstanceState(savedInstanceState);
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
            descriptionLabel.setText(mItem.getLongDescription());

            TextView questionCountLabel = (TextView) detailView.findViewById(R.id.numberQuestionsLabel);
            questionCountLabel.setText(Integer.toString(mItem.getQuestions().size()) + (mItem.getQuestions().size() == 1 ? " question" : " questions"));

            Button beginTopicButton = (Button) detailView.findViewById(R.id.beginTopicButton);
            beginTopicButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startQuiz();
                }
            });
            if (mItem.getQuestions().size() == 0) {
                beginTopicButton.setEnabled(false);
            }

        }

        return detailView;
    }

    public void startQuiz() {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_left);

        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setRemainingQuestions(new ArrayList<Question>(mItem.getQuestions()));
        questionFragment.setTotalCount(0);
        questionFragment.setCorrectCount(0);
        questionFragment.setCurrentQuestion(questionFragment.getRemainingQuestions().remove(0));

        ft.replace(getId(), questionFragment);

        ft.commit();
    }
}
