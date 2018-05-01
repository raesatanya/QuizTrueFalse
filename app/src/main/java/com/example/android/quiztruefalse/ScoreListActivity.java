package com.example.android.quiztruefalse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.quiztruefalse.Adapter.ScoreLists;
import com.example.android.quiztruefalse.kelasobjek.ScoreObjek;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.android.quiztruefalse.ListKuisActivity.KUIS_ID;
import static com.example.android.quiztruefalse.ResultActivity.REF_SCORE_KUIS;

public class ScoreListActivity extends AppCompatActivity {

    private ListView listscore;
    private Button dismiss;
    private ArrayList<ScoreObjek> scoreObjeks;
    String idkuis;

    private DatabaseReference refRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);

        Intent i = getIntent();
        idkuis =  i.getStringExtra(KUIS_ID);

        listscore = findViewById(R.id.list_score);
        dismiss = findViewById(R.id.btn_dismiss_score);

        scoreObjeks = new ArrayList<>();
        refRoot = FirebaseDatabase.getInstance().getReference();

        dismiss.setVisibility(View.GONE);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference refScore = refRoot.child(REF_SCORE_KUIS).child(idkuis);
        refScore.orderByChild("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    ScoreObjek score = data.getValue(ScoreObjek.class);
                    scoreObjeks.add(score);
                }
                Collections.reverse(scoreObjeks);
                ScoreLists scoreAdapter = new ScoreLists(ScoreListActivity.this, scoreObjeks);
                listscore.setAdapter(scoreAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
