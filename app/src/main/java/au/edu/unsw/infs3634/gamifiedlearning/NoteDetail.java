package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteDetail extends AppCompatActivity {

    private TextView mTitleofNoteDetail,mContentofNoteDetail;
    FloatingActionButton mGotoEditNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        mTitleofNoteDetail = findViewById(R.id.tv_TitleNoteDetail);
        mContentofNoteDetail = findViewById(R.id.contentofNoteDetail);
        mGotoEditNote = findViewById(R.id.fa_GotoEditNote);

        Intent data = getIntent();

        mGotoEditNote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),EditNoteActivity.class);
                intent.putExtra("title",data.getStringExtra("title"));
                intent.putExtra("content",data.getStringExtra("content"));
                intent.putExtra("noteId",data.getStringExtra("noteId"));
                v.getContext().startActivity(intent);
            }
        });

        mContentofNoteDetail.setText(data.getStringExtra("content"));
        mTitleofNoteDetail.setText(data.getStringExtra("title"));

    }

}