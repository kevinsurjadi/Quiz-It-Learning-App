package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
public class HomePageActivity extends AppCompatActivity implements RecyclerAdapter.OnNoteListener {
    private List<Category> categoryList;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.recyclerView);
        categoryList = Category.getCategories();

        name = (TextView) findViewById(R.id.tvName);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myProfRef = database.getReference(FirebaseAuth.getInstance().getUid()).child("Name");
        DatabaseReference myLevRef = database.getReference(FirebaseAuth.getInstance().getUid()).child("Level");
        DatabaseReference myScoRef = database.getReference(FirebaseAuth.getInstance().getUid()).child("Score");

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        // sets the Welcome name to the logged-in user's display name
        myProfRef.setValue(user.getDisplayName());
        name.setText(user.getDisplayName());

        // sets the default level to 0 if the user has just joined
        myLevRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    myLevRef.setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // sets the default score to 0 if the user has just joined
        myScoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    myScoRef.setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        setAdapter();
    }

    // setting the adapter
    private void setAdapter() {
        adapter = new RecyclerAdapter(categoryList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    // sets up the list so that it can be clicked to reference intent
    @Override
    public void onNoteClick(int position) {
        categoryList.get(position);
        Intent intent = new Intent(HomePageActivity.this, ModeSelectActivity.class);
        intent.putExtra("Category",categoryList.get(position).getCategory());
        intent.putExtra("Description", categoryList.get(position).getDescription());
        startActivity(intent);
    }

    // setting up the menu with search bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.searchItem);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    // setting up functional parts of the menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_notes:
                openNotes();
                return true;
            case R.id.nav_FAQ:
                openFAQ();
                return true;
            case R.id.nav_profile:
                openProfile();
                return true;
            case R.id.nav_topics:
                openTopics();
                return true;
            case R.id.nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);

                builder.setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                openLogin();
                            }
                        })
                        .setNegativeButton("Cancel", null);

                AlertDialog alert = builder.create();
                alert.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openProfile() {
        Intent intent = new Intent(HomePageActivity.this, profileActivity.class);
        startActivity(intent);
    }

    public void openTopics() {
        Intent intent = new Intent(HomePageActivity.this, HomePageActivity.class);
        startActivity(intent);
    }

    public void openLogin() {
        Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void openNotes() {
        Intent intent = new Intent(HomePageActivity.this, NotesActivity.class);
        startActivity(intent);
    }

    public void openFAQ() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forums.pixeltailgames.com"));
        startActivity(intent);
    }

    // removes the ability to go back to login
    @Override
    public void onBackPressed() {

    }

}