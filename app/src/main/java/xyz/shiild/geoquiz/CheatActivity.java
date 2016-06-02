package xyz.shiild.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    /********** Fields **********/

    /** Key for the extra for answerIsTrue. */
    private static final String EXTRA_ANSWER_IS_TRUE = "xyz.shiild.geoquiz.answer_is_true";

    /** Constant for the extra's key. */
    private static final String EXTRA_ANSWER_SHOWN = "xyz.shiild.geoquiz.answer_shown";

    /** Stores the value of the answerIsTrue extra when it is retrieved. */
    private boolean mAnswerIsTrue;

    /** */
    private TextView mAnswerTextView;

    /** */
    private Button mShowAnswer;

    /********** Methods **********/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        // Retrieve the value of the extra and store it in mAnswerIsTrue.
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        //
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            // Next, set the TextView's text using TextView.setText(int).
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText((R.string.false_button));
                }
                setAnswerShownResult(true);
            }
        });
    }

    /**
     * In order to pass some specific data back to QuizActivity, an Intent is created and an
     * extra is put on it. Then Activity.setResult is called to send that data back to parent.
     * This method is called in the ShowAnswer button's listener in onCreate(...).
     *
     * @param isAnswerShown the data result to send to parent.
     */
    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent(); // create an Intent
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown); // put an extra on it
        setResult(RESULT_OK, data); // send the result code and intent (data) back to QuizActivity
    }

    /**
     * Creates an Intent properly configured with the extra that CheatActivity will need.
     *
     * @param packageContext    the context in which the Activity is located.
     * @param answerIsTrue      put into the intent with a private name using
     *                          the EXTRA_ANSWER_IS_TRUE argument.
     * @return the Intent with the extra included in it.
     */
    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    /**
     * Decodes the contents of the result Intent into something usable by other classes,
     * e.g. QuizActivity.
     * @param result    the intent containing the result.
     * @return The result to be decoded.
     */
    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }
}
