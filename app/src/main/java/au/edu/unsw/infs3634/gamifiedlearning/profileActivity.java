package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class profileActivity extends AppCompatActivity {
    private static final String TAG = QuizModeActivity.class.getSimpleName();
    private TextView score;
    private TextView level;
    private TextView levelL;
    private TextView levelR;
    private TextView name;
    private ProgressBar progress;
    private Button share;
    private List<LeaderBoard> leaderBoardList;
    private ImageView back;

    private RecyclerView recyclerView;
    private LeaderBoardRecyclerAdapter adapter;

    Intent shareIntent;
    String shareBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerView2);

        score = (TextView) findViewById(R.id.tvScore);
        level = (TextView) findViewById(R.id.tvLevel);
        levelL = (TextView) findViewById(R.id.tvLevelCurrent);
        levelR = (TextView) findViewById(R.id.tvLevelNext);
        name = (TextView) findViewById(R.id.tvProfileName);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        share = (Button) findViewById(R.id.btn_share2);
        back = (ImageView) findViewById(R.id.btnBackProfile);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        // sets display name to whoever is logged in
        name.setText(user.getDisplayName());

        leaderBoardList = new ArrayList<LeaderBoard>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseAuth.getInstance().getUid()).child("Score");
        DatabaseReference mySecRef = database.getReference(FirebaseAuth.getInstance().getUid()).child("Level");
        DatabaseReference myLeadRef = database.getReference();

        // logic for inputting the data stored in firebase into a list for recyclerView
        myLeadRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();

                for(DataSnapshot child : children) {
                    LeaderBoard data = child.getValue(LeaderBoard.class);
                    leaderBoardList.add(data);
                }
                adapter = new LeaderBoardRecyclerAdapter(leaderBoardList);
                recyclerView.setAdapter(adapter);
                sortArrayListTotalConfirmed();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        setAdapter();

        // logic to see if points exist and need to be shown
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int total = Integer.parseInt(snapshot.getValue().toString());
                    score.setText(String.format("%d points", total));
                    progress.setProgress(total);
                } else {
                    score.setText("0 points");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Sets up the share to social media
        mySecRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int netLev = Integer.parseInt(snapshot.getValue().toString());
                    level.setText(String.format("Level %d", netLev));
                    levelL.setText(String.format("Level %d", netLev));
                    levelR.setText(String.format("Level %d", netLev+1));
                    shareBody = String.format("Come join me on Popcorn! Im level %d - can you beat me ?!", netLev);
                } else {
                    level.setText("Level 0");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/pain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Test Subject");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
    }

    public void openHome() {
        Intent intent = new Intent(profileActivity.this, HomePageActivity.class);
        startActivity(intent);
    }

    private void setAdapter() {
        adapter = new LeaderBoardRecyclerAdapter(leaderBoardList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    // orders the leaderboard based on the level
    private void sortArrayListTotalConfirmed() {
        Collections.sort(leaderBoardList, new Comparator<LeaderBoard>() {

            @Override
            public int compare(LeaderBoard o1, LeaderBoard o2) {
                return String.valueOf(o2.getLevel()).compareTo(String.valueOf(o1.getLevel()));
            }
        });
        adapter.notifyDataSetChanged();
    }
}