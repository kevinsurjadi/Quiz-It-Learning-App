package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizModeActivity extends AppCompatActivity {
    private static final String TAG = QuizModeActivity.class.getSimpleName();

    private List<Result> quiz;
    private TextView question;
    private TextView tvCounter;
    private Button btnA;
    private Button btnB;
    private Button btnC;
    private Button btnD;
    private ImageView back;

    private String q;
    private String a1;
    private String a2;
    private String a3;
    private String a4;

    private Button next;
    private int counter = 1;
    private int points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_mode);

        getSupportActionBar().hide();

        next = (Button) findViewById(R.id.btnContinue);
        question = (TextView) findViewById(R.id.tvQuestion);
        tvCounter = (TextView) findViewById(R.id.tv_counter);
        btnA = (Button) findViewById(R.id.btnAnswerA);
        btnB = (Button) findViewById(R.id.btnAnswerB);
        btnC = (Button) findViewById(R.id.btnAnswerC);
        btnD = (Button) findViewById(R.id.btnAnswerD);
        back = (ImageView) findViewById(R.id.quiz_back);
        tvCounter.setText("1 of 5");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertExit();
            }
        });

        newQuiz();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                if (counter > 5) {
                    finishQuiz();
                } else {
                    newQuiz();
                    tvCounter.setText(String.format("%d of 5", counter));
                }
            }
        });
    }

    public void finishQuiz() {
        Intent intent = new Intent(QuizModeActivity.this, QuizResultActivity.class);
        intent.putExtra("Score", String.valueOf(points));
        startActivity(intent);
    }

    public void newQuiz() {
        View.OnClickListener answerButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonClicked = (Button) v;

                String answerSelected = buttonClicked.getText().toString();
                String log = answerSelected.equals(a1) ? "Correct" : "Incorrect";

                Toast.makeText(QuizModeActivity.this, log, Toast.LENGTH_SHORT).show();

                // logic to test if user is on 5 or less questions
                counter++;
                tvCounter.setText(String.format("%d of 5", counter));
                    if(answerSelected.equals(a1)) {
                        points++;
                        newQuiz();
                        if (counter > 5) {
                            finishQuiz();
                        } else {
                            newQuiz();
                        }
                    } else {
                        if (counter > 5) {
                            finishQuiz();
                        } else {
                            newQuiz();
                        }
                    }
            }
        };

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                btnA.setOnClickListener(answerButtonOnClickListener);
                btnB.setOnClickListener(answerButtonOnClickListener);
                btnC.setOnClickListener(answerButtonOnClickListener);
                btnD.setOnClickListener(answerButtonOnClickListener);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuizService service = retrofit.create(QuizService.class);

        // checks on new thread what category and difficulty quiz selected is to pull from the API
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Call<Response> responseCall;

                switch (getIntent().getStringExtra("Category, difficulty")) {
                    case "Film, easy":
                        responseCall = service.getFilmEasy();
                        break;
                    case "Film, medium":
                        responseCall = service.getFilmMedium();
                        break;
                    case "Film, hard":
                        responseCall = service.getFilmHard();
                        break;
                    case "Books, easy":
                        responseCall = service.getBookEasy();
                        break;
                    case "Books, medium":
                        responseCall = service.getBookMedium();
                        break;
                    case "Books, hard":
                        responseCall = service.getBookHard();
                        break;
                    case "Musicals & Theatres, easy":
                        responseCall = service.getTheatreEasy();
                        break;
                    case "Musicals & Theatres, medium":
                        responseCall = service.getTheatreMedium();
                        break;
                    case "Musicals & Theatres, hard":
                        responseCall = service.getTheatreHard();
                        break;
                    case "Music, easy":
                        responseCall = service.getMusicEasy();
                        break;
                    case "Music, medium":
                        responseCall = service.getMusicMedium();
                        break;
                    case "Music, hard":
                        responseCall = service.getMusicHard();
                        break;
                    case "Television, easy":
                        responseCall = service.getTVEasy();
                        break;
                    case "Television, medium":
                        responseCall = service.getTVMedium();
                        break;
                    case "Television, hard":
                        responseCall = service.getTVHard();
                        break;
                    case "Video Games, easy":
                        responseCall = service.getGameEasy();
                        break;
                    case "Video Games, medium":
                        responseCall = service.getGameMedium();
                        break;
                    case "Video Games, hard":
                        responseCall = service.getGameHard();
                        break;
                    case "Board Games, easy":
                        responseCall = service.getBoardEasy();
                        break;
                    case "Board Games, medium":
                        responseCall = service.getBoardMedium();
                        break;
                    case "Board Games, hard":
                        responseCall = service.getBoardHard();
                        break;
                    case "Japanese Anime & Manga, easy":
                        responseCall = service.getAnimeEasy();
                        break;
                    case "Japanese Anime & Manga, medium":
                        responseCall = service.getAnimeMedium();
                        break;
                    case "Japanese Anime & Manga, hard":
                        responseCall = service.getAnimeHard();
                        break;
                    case "Cartoon & Animations, easy":
                        responseCall = service.getCartoonEasy();
                        break;
                    case "Cartoon & Animations, medium":
                        responseCall = service.getCartoonMedium();
                        break;
                    case "Cartoon & Animations, hard":
                        responseCall = service.getCartoonHard();
                        break;
                    default:
                        responseCall = service.getResponse();
                        break;
                }

                responseCall.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        quiz = response.body().getResults();

                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                q = Jsoup.clean(quiz.get(0).getQuestion(), new Whitelist());
                                a1 = Jsoup.clean(quiz.get(0).getCorrectAnswer(), new Whitelist());
                                a2 = Jsoup.clean(quiz.get(0).getIncorrectAnswers().get(0), new Whitelist());
                                a3 = Jsoup.clean(quiz.get(0).getIncorrectAnswers().get(1), new Whitelist());
                                a4 = Jsoup.clean(quiz.get(0).getIncorrectAnswers().get(2), new Whitelist());
                                newQuestion();
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
            }
        });


    }
    public void newQuestion() {
        Random rand = new Random();

        String[] answers = {a1, a2, a3, a4};
        shuffle(answers);

        question.setText(q);

        // removes the HTML string from the API to regular text
        btnA.setText(Jsoup.clean(String.valueOf(answers[0]), new Whitelist()));
        btnB.setText(Jsoup.clean(String.valueOf(answers[1]), new Whitelist()));
        btnC.setText(Jsoup.clean(String.valueOf(answers[2]), new Whitelist()));
        btnD.setText(Jsoup.clean(String.valueOf(answers[3]), new Whitelist()));
    }

    // randomise ordering of the answers logic
    public String[] shuffle(String[] answers) {
        String temp;
        int k;
        Random rand = new Random();

        for (int i = 0; i < 4; i++) {
            k = rand.nextInt(4);
            temp = answers[i];
            answers[i] = answers[k];
            answers[k] = temp;
        }
        return answers;
    }

    public void openTopics() {
        Intent intent = new Intent(QuizModeActivity.this, HomePageActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        alertExit();
    }

    // sets up an alert asking if users really want to exit quiz
    public void alertExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizModeActivity.this);
        builder.setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openTopics();
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog alert = builder.create();
        alert.show();
    }
}