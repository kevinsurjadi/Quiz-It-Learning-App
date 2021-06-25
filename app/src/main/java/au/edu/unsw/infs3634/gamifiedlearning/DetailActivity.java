package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private TextView name;
    private Button easy;
    private Button medium;
    private Button hard;
    private String difficulty;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // removes toolbar
        getSupportActionBar().hide();

        name = (TextView) findViewById(R.id.tvDifficulty);
        easy = (Button) findViewById(R.id.btnEasy);
        medium = (Button) findViewById(R.id.btnMedium);
        hard = (Button) findViewById(R.id.btnHard);
        back = (ImageView) findViewById(R.id.btnBack);

        name.setText(getIntent().getStringExtra("Category"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTopics();
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = "easy";
                clickMode();
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = "medium";
                clickMode();
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = "hard";
                clickMode();
            }
        });
    }

    public void clickMode() {
        Intent intent = new Intent(DetailActivity.this, QuizModeActivity.class);
        intent.putExtra("Category, difficulty",String.format("%s, %s", getIntent().getStringExtra("Category"), difficulty));
        startActivity(intent);
    }

    public void openTopics() {
        Intent intent = new Intent(DetailActivity.this, ModeSelectActivity.class);
        intent.putExtra("Category", getIntent().getStringExtra("Category"));
        intent.putExtra("Description", getIntent().getStringExtra("Description"));
        startActivity(intent);
    }
}