package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesActivity extends AppCompatActivity {

    FloatingActionButton mfaCreateNotes;
    private FirebaseAuth firebaseAuth;

    RecyclerView mRecyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter<firebaseModel, NoteViewHolder> noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        mfaCreateNotes = findViewById(R.id.faCreateNotes);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mfaCreateNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NotesActivity.this, CreateNoteActivity.class));

            }
        });

        // this path pulls from the firebaseFirestore db
        Query query = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").orderBy("title", Query.Direction.ASCENDING);

        //this data will be put into the allUserNotes
        FirestoreRecyclerOptions<firebaseModel> allUserNotes = new FirestoreRecyclerOptions.Builder<firebaseModel>().setQuery(query,firebaseModel.class).build();

        //Recycler for the Firebase pulled items
        noteAdapter = new FirestoreRecyclerAdapter<firebaseModel, NoteViewHolder>(allUserNotes) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int position, @NonNull firebaseModel model) {

                // menu items
                ImageView popupButton = noteViewHolder.itemView.findViewById(R.id.ivMenuPopup);
                // random colour generator for the notes
                int colourcode = getRandomColor();
                noteViewHolder.mNote.setBackgroundColor(noteViewHolder.itemView.getResources().getColor(colourcode,null));


                noteViewHolder.noteTitle.setText(model.getTitle());
               noteViewHolder.noteContent.setText(model.getContent());

               String docId = noteAdapter.getSnapshots().getSnapshot(position).getId();
                // send the title content and noteids to the NoteDetailScreen
               noteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent = new Intent(v.getContext(),NoteDetail.class);
                       intent.putExtra("title",model.getTitle());
                       intent.putExtra("content",model.getContent());
                       intent.putExtra("noteId",docId);

                       v.getContext().startActivity(intent);
                   }
               });

               popupButton.setOnClickListener(new View.OnClickListener() {
                   // when edit is clicked get Title and Content
                   @Override
                   public void onClick(View v) {
                       PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                       popupMenu.setGravity(Gravity.END);
                       popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                           @Override
                           public boolean onMenuItemClick(MenuItem item) {
                               Intent intent = new Intent(v.getContext(),EditNoteActivity.class);
                               intent.putExtra("title",model.getTitle());
                               intent.putExtra("content",model.getContent());
                               intent.putExtra("noteId",docId);

                               v.getContext().startActivity(intent);

                               return false;
                           }
                       });

                       //delete note
                       popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                           @Override
                           public boolean onMenuItemClick(MenuItem item) {

                               DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(docId);
                               documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {

                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       Toast.makeText(v.getContext(),"This note has been deleted",Toast.LENGTH_SHORT).show();
                                   }
                               }).addOnFailureListener(new OnFailureListener() {

                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                       Toast.makeText(v.getContext(),"Failed to delete",Toast.LENGTH_SHORT).show();
                                   }
                               });
                               return false;
                           }
                       });

                       popupMenu.show();

                   }
               });

            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout,parent,false);
                return new NoteViewHolder(view);
            }
        };

        mRecyclerView = findViewById(R.id.rvNotes);
        mRecyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setAdapter(noteAdapter);

    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView noteTitle;
        private TextView noteContent;
        LinearLayout mNote;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.tvNotesTitle);
            noteContent = itemView.findViewById(R.id.tvNoteContent);
            mNote = itemView.findViewById(R.id.ll_Note);

        }
    }

    // whenever we get onto this screen refresh the database laod
    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(noteAdapter != null) {
            noteAdapter.stopListening();
        }
    }

    //change notes to random colours
    private int getRandomColor() {
        List<Integer> colorcode = new ArrayList<>();
        colorcode.add(R.color.p1_green);
        colorcode.add(R.color.p2_lightgreen);
        colorcode.add(R.color.p3_lightpurple);
        colorcode.add(R.color.p4_lightteal);
        colorcode.add(R.color.p5_lightgreyblue);
        colorcode.add(R.color.p6_lightyellow);
        colorcode.add(R.color.p7_orangey);
        colorcode.add(R.color.p8_pinky);
        colorcode.add(R.color.p9_morepinky);
        colorcode.add(R.color.p10_lightgrey);
        colorcode.add(R.color.p11_anotherpurple);
        colorcode.add(R.color.p12_skin);

        Random random = new Random();
        int number = random.nextInt(colorcode.size());
        return colorcode.get(number);
    }

}