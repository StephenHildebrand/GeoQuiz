package xyz.shiild.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    /** The True button. */
    private Button mTrueButton;
    /** The False button. */
    private Button mFalseButton;
    /** The Next button. */
    private ImageButton mNextButton;
    /** The Previous button. */
    private ImageButton mPrevButton;
    /** The Question text view. */
    private TextView mQuestionTextView;
    /** Array of Question objects. Would be created/stored elsewhere in a more complex project. */
    private Question[] mQuestionBank = new Question[] {
        new Question(R.string.question_oceans, true),
        new Question(R.string.question_mideast, false),
        new Question(R.string.question_africa, false),
        new Question(R.string.question_americas, true),
        new Question(R.string.question_asia, true)
    };
    /** Index for the array of question objects. */
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Get a reference for the TextView and set its text to the question at the current index.
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view); // TextView reference.
        updateQuestion();

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        // Get a reference to the Next button.
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        // Set a listener on it to increment the index and update the TextView's text.
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Point the current index to the next Question.
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        // Set a listener for the Question TextView as well such that clicking it will also
        // proceed to the next question (just like clicking the next Button).
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Point the current index to the next Question.
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        // Get a reference to the Previous button.
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        // Set a listener on it to decrement the index and update the TextView's text.
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Point the current index to the previous question.
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        // Update this Question to the next one.
        updateQuestion();
    }

    /**
     * Update the current Question in the TextView to the Question at current index.
     */
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    /**
     * Accepts a boolean variable that identifies whether the user pressed the True or False
     * button. Then, it will check the user's answer against the answer in the current Question
     * object. Finally, after determining whether the user answered correctly, it will make a
     * Toast that displays the appropriate message to the user.
     * @param userPressedTrue   True if user presses the True button, False if the False button.
     */
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.quiz, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
