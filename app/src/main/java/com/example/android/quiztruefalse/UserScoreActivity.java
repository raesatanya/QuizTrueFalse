package com.example.android.quiztruefalse;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.quiztruefalse.Adapter.ScoreLists;
import com.example.android.quiztruefalse.Adapter.ScoreUserLists;
import com.example.android.quiztruefalse.kelasobjek.ScoreObjek;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.android.quiztruefalse.ListKuisActivity.KUIS_ID;
import static com.example.android.quiztruefalse.ResultActivity.REF_SCORE_KUIS;
import static com.example.android.quiztruefalse.ResultActivity.REF_SCORE_USER;

public class UserScoreActivity extends AppCompatActivity {


    private ListView listscore;
    private ArrayList<ScoreObjek> listScoreObjeks;
    String idkuis;

    private DatabaseReference refRoot;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_score);

        Intent i = getIntent();
        idkuis = i.getStringExtra(KUIS_ID);
        Button dismiss;

        listscore = findViewById(R.id.list_score);
        dismiss = findViewById(R.id.btn_dismiss_score);

        listScoreObjeks = new ArrayList<>();
        refRoot = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        dismiss.setVisibility(View.GONE);

        listscore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                showDialogListScore(listScoreObjeks.get(i));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference refScore = refRoot.child(REF_SCORE_USER).child(mUser.getUid());
        refScore.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listScoreObjeks.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ScoreObjek score = data.getValue(ScoreObjek.class);
                    listScoreObjeks.add(score);
                }
                ScoreUserLists scoreAdapter = new ScoreUserLists(UserScoreActivity.this, listScoreObjeks);
                listscore.setAdapter(scoreAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showDialogListScore(final ScoreObjek scoreObjek) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.list_layout, null);
        dialogBuilder.setView(dialogView);

        final ArrayList<ScoreObjek> listScore = new ArrayList<>();
        final ListView listView = dialogView.findViewById(R.id.list_score);
        final Button dismiss = dialogView.findViewById(R.id.btn_dismiss_score);

        DatabaseReference refScoreKuis = refRoot.child(REF_SCORE_KUIS).child(scoreObjek.getIdKuis());

        dialogBuilder.setTitle(scoreObjek.getNamaKuis());
        final AlertDialog b = dialogBuilder.create();

        refScoreKuis.orderByChild("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listScore.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    listScore.add(data.getValue(ScoreObjek.class));
                }
                Collections.reverse(listScore);

                final ScoreLists scoreLists = new ScoreLists(UserScoreActivity.this, listScore);
                listView.setAdapter(scoreLists);
                b.show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

    }
}
