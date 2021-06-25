package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LearnActivity extends AppCompatActivity {
    private List<Result> quiz;
    private RecyclerView recyclerView;
    private LearnRecyclerAdapter adapter;
    private ImageView back;
    private static final String TAG = QuizModeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        // removes toolbar for UI
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.learnRecycler);
        back = (ImageView) findViewById(R.id.btnBackLearn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTopics();
            }
        });

        String category = getIntent().getStringExtra("Category");

        // connecting to the API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuizService service = retrofit.create(QuizService.class);
        Call<Response> responseCall = service.getResponse();

        // checks which service getter to call
        switch (category) {
            case "Books":
                responseCall = service.getBook();
                break;
            case "Film":
                responseCall = service.getFilm();
                break;
            case "Music":
                responseCall = service.getMusic();
                break;
            case "Musicals & Theatres":
                responseCall = service.getTheatre();
                break;
            case "Television":
                responseCall = service.getTV();
                break;
            case "Video Games":
                responseCall = service.getGame();
                break;
            case "Board Games":
                responseCall = service.getBoard();
                break;
            case "Japanese Anime & Manga":
                responseCall = service.getAnime();
                break;
            case "Cartoon & Animations":
                responseCall = service.getCartoon();
                break;
            default:
                responseCall = service.getResponse();
                break;
        }

        // assigns the API to the quiz List
        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                quiz = response.body().getResults();
                setAdapter();
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    public void openTopics() {
        Intent intent = new Intent(LearnActivity.this, ModeSelectActivity.class);
        intent.putExtra("Category", getIntent().getStringExtra("Category"));
        intent.putExtra("Description", getIntent().getStringExtra("Description"));
        startActivity(intent);
    }

    // setting the adapter
    private void setAdapter() {
        adapter = new LearnRecyclerAdapter(quiz);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}