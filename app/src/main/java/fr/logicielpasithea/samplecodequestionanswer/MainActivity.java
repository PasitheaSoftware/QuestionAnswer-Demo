/**
 * Sample code for the question/answer function of PASITHEA.
 * This code is free of use and can be modified without restriction.
 * This sample code is bilingual French/English.
 *
 * Before testing it, you must contact us to request a temporary password to connect to our maven repository.
 *
 * @author Pasithea Software (contact@logicielpasthiea.fr)
 *
 */
package fr.logicielpasithea.samplecodequestionanswer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.pasithea.pasithea.Pasithea;
import com.software.pasithea.pasithea.PasitheaBuilder;
import com.software.pasithea.pasithea.onAnswerListener;
import com.software.pasithea.pasithea.onInitListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DemoQuestionAnswer";

    private static final int AUDIO_REQUEST = 1;
    private static final int STORAGE_REQUEST = 2;

    private static TextView QuestionTv;
    private static TextView AnswerTv;
    private static TextView ActionTv;
    private static ImageView EmojiIv;
    private static Button RestartBtn;

    private Locale mLocale;

    private static String[] answerWords = new String[2];
    private static String question;

    Pasithea mPasithea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuestionTv = (TextView) findViewById(R.id.question_tv);
        AnswerTv = (TextView) findViewById(R.id.answer_tv);
        ActionTv = (TextView) findViewById(R.id.action_tv);
        EmojiIv = (ImageView) findViewById(R.id.emoji_iv);
        RestartBtn = (Button) findViewById(R.id.restart_btn);

        mLocale = getResources().getConfiguration().locale;

        RestartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnswer();
            }
        });

        answerWords[0] = getString(R.string.answer_yes);
        answerWords[1] = getString(R.string.answer_no);
        question = getString(R.string.question);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkRecordAudioPermissions();
    }

    /*
    StartAnswer() starts the question/answer session.
    It is set as action triggered by the listener once the configuration is done.
    */
    public void startAnswer(){
        // This method is called after the initialization is done.
        // So we can create an instance of PASITHEA with getInstance().
        final Pasithea answerPasithea = Pasithea.getInstance();
        answerPasithea.startQuestionAnswer(question, answerWords, new onAnswerListener() {

            //Action when the first keyword is detected.
            @Override
            public void onAnswerYes() {
                QuestionTv.setText(question);
                AnswerTv.setText(getString(R.string.answer_yes));
                ActionTv.setText(getString(R.string.action_happy));
                EmojiIv.setImageResource(R.drawable.smile_android_emoji);
                /*The voice message can overlap the GUI changes.
                To avoid that we postpone it with a Handler().postDelayed(Runnable).
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        answerPasithea.saySomething(getString(R.string.speech_happy));
                    }
                }, 500);
            }

            //Action when the second keyword is detected.
            @Override
            public void onAnswerNo() {
                QuestionTv.setText(question);
                AnswerTv.setText(getString(R.string.answer_no));
                ActionTv.setText(getString(R.string.action_sad));
                EmojiIv.setImageResource(R.drawable.sad_android_emoji);

                /*The voice message can overlap the GUI changes.
                To avoid that we postpone it with a Handler().postDelayed(Runnable).
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        answerPasithea.saySomething(getString(R.string.speech_sad));
                    }
                }, 500);
            }

            //Action triggered when the speech recognition engine does not recognize the spoken keyword.
            @Override
            public void onAnswerUnk() {
                answerPasithea.unkAnswer();
                answerPasithea.restartQuestionAnswer();
            }
        });
    }

    /*
     Because the permission check must be done inside an activity, we have to execute it here.
     This code needs the RECORD_AUDIO and READ_EXTERNAL_STORAGE permissions.
     We set the initialiation of PASITHEA as callback method.
     */
    private void checkRecordAudioPermissions() {
        Log.i(TAG, "checkRecordAudioPermissions: start");
        int recordCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if (recordCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, AUDIO_REQUEST);
            Log.i(TAG, "checkRecordAudioPermissions: end");
        } else {
            checkStoragePermissions();
            Log.i(TAG, "checkRecordAudioPermissions: end");
        }
    }

    private void checkStoragePermissions() {
        Log.i(TAG, "checkStoragePermissions: start");
        int recordCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (recordCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_REQUEST);
            Log.i(TAG, "checkRecordAudioPermissions: end");
        } else {
            setPasithea();
            Log.i(TAG, "checkStoragePermissions: end");
        }
    }

    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strings, @NonNull int[] ints) {
        Log.d(TAG, "onRequestPermissionsResult: Enter result");
        if(i == AUDIO_REQUEST) {
            checkStoragePermissions();
        }
        if(i == STORAGE_REQUEST){
            setPasithea();
        }
    }

    private Pasithea setPasithea(){
        return mPasithea = new PasitheaBuilder().setValues(this, this).
                setInitListener(new onInitListener() {
                    @Override
                    public void InitDone() {
                        startAnswer();
                    }
                }).build();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPasithea.stopPasithea();
    }
}