package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ModeSelectActivity extends AppCompatActivity {
    private Button learn;
    private Button quiz;
    private Button more;
    private TextView topic;
    private ImageView back;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select);

        getSupportActionBar().hide();

        learn = (Button) findViewById(R.id.btn_learn);
        quiz = (Button) findViewById(R.id.btn_quiz);
        more = (Button) findViewById(R.id.btn_more);
        topic = (TextView) findViewById(R.id.topic);
        topic.setText(getIntent().getStringExtra("Category"));
        back = (ImageView) findViewById(R.id.btnBackSelect);
        description = (TextView) findViewById(R.id.tvDescription);
        description.setText(getIntent().getStringExtra("Description"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTopics();
            }
        });

        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLearn();
            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuiz();
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                learnMore();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reg_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_profile:
                openProfile();
                return true;
            case R.id.nav_topics:
                openTopics();
                return true;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                openLogin();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openProfile() {
        Intent intent = new Intent(ModeSelectActivity.this, profileActivity.class);
        startActivity(intent);
    }

    public void openTopics() {
        Intent intent = new Intent(ModeSelectActivity.this, HomePageActivity.class);
        startActivity(intent);
    }

    public void openLogin() {
        Intent intent = new Intent(ModeSelectActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void openLearn() {
        Intent intent = new Intent(ModeSelectActivity.this, LearnActivity.class);
        intent.putExtra("Category",getIntent().getStringExtra("Category"));
        startActivity(intent);
    }

    public void openQuiz() {
        Intent intent = new Intent(ModeSelectActivity.this, DetailActivity.class);
        intent.putExtra("Category",getIntent().getStringExtra("Category"));
        intent.putExtra("Description", getIntent().getStringExtra("Description"));
        startActivity(intent);
    }

    public void learnMore() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/" + getIntent().getStringExtra("Category")));
        startActivity(intent);
    }
}