package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoard {
    private String Name;
    private long Level;
    private long Score;

    public LeaderBoard() {
    }

    public LeaderBoard(String name, long level, long score) {
        Name = name;
        Level = level;
        Score = score;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public long getLevel() {
        return Level;
    }

    public void setLevel(long level) {
        Level = level;
    }

    public long getScore() {
        return Score;
    }

    public void setScore(long score) {
        Score = score;
    }
}
