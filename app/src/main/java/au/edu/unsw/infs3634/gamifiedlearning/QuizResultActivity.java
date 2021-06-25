package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Executable;
import java.util.concurrent.Executors;

public class QuizResultActivity extends AppCompatActivity {
    private Button finish;
    private int score;
    private Button share;
    private TextView tvPoints;
    private TextView cheerTop;
    private TextView cheerBtm;
    private long value;
    private long level;

    Intent shareIntent;
    String shareBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        getSupportActionBar().hide();

        finish = (Button) findViewById(R.id.btn_finish);
        share = (Button) findViewById(R.id.btn_share);
        tvPoints = (TextView) findViewById(R.id.tvPoints);
        cheerTop = (TextView) findViewById(R.id.tvCheer);
        cheerBtm = (TextView) findViewById(R.id.tvComment);

        // Sets the score value to what the user got in the quiz from scene before
        score = Integer.valueOf(getIntent().getStringExtra("Score"));
        tvPoints.setText(String.format("%d points", score));

        // conditions for the text that appears, noting the quiz is out of 5
        if (score==5){
            cheerTop.setText("Amazing!");
            cheerBtm.setText("");
        } else if (score==4){
            cheerTop.setText("So close!");
            cheerBtm.setText("You almost got all the questions correct. Better luck next time!");
        } else {
            cheerTop.setText("Bad Luck!");
            cheerBtm.setText("Keep practicing to improve your score!");
        }

        // Setting up the contents of the share
        shareBody = String.format("I just scored %d points on Popcorn - Try beat my score!!", score);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/pain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Beat My Score");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishQuiz();
            }
        });
    }

    // disables the back button, and makes it do what finishing quiz button does
    @Override
    public void onBackPressed() {
        finishQuiz();
    }

    // setting up the onClickListener so that when users click finish, the score and level firebase is updated
    public void finishQuiz() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseAuth.getInstance().getUid()).child("Score");
        DatabaseReference mySecRef = database.getReference(FirebaseAuth.getInstance().getUid()).child("Level");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    value = (long) snapshot.getValue();
                    value = value + score;
                    if(value>=100) {
                        value = value - 100;
                        snapshot.getRef().setValue(value);
                        mySecRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()) {
                                    level = (long) snapshot.getValue();
                                    level = level +1;
                                    snapshot.getRef().setValue(level);
                                } else {
                                    mySecRef.setValue(1);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        snapshot.getRef().setValue(value);
                    }
                } else {
                    myRef.setValue(score);
                }
                Intent intent = new Intent(QuizResultActivity.this, HomePageActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}