package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        // splash screen length
        Handler mHandler = new Handler();
        mHandler.postDelayed(mLaunchApp, 300);
    }

    private Runnable mLaunchApp = new Runnable() {
        public void run() {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
    };
}