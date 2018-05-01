package com.example.android.quiztruefalse;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.quiztruefalse.Adapter.KuisLists;
import com.example.android.quiztruefalse.kelasobjek.KuisObjek;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ListKuisActivity extends AppCompatActivity {


    public static final String REF_TESTKUIS = "testKuis";
    public static final String KUIS_ID = "id_testkuis";
    public static final String NAMA_KUIS = "namaKuis";
    public static final String ONE_TIME = "one_time";

    private ImageButton mSearch;

    EditText searchKode ;
    ListView listViewkuis;

    ArrayList<KuisObjek> listKuis;

    DatabaseReference databaseReference;
    DatabaseReference refKuis;
    String muser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kuis);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        refKuis = databaseReference.child(REF_TESTKUIS);
        mSearch = findViewById(R.id.searchKodeKuis);
        searchKode = findViewById(R.id.textSearchKodeKuis);
        listViewkuis = findViewById(R.id.list_kuis);
        listKuis = new ArrayList<>();
        muser = FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0];


        listViewkuis.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                KuisObjek kuisObjek = listKuis.get(i);

                showDialogInsertPass(kuisObjek);
            }
        });

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textsearch = searchKode.getText().toString() ;
                displaySearch(textsearch);
            }
        });
    }

    private void displaySearch(String textsearch) {
        refKuis.orderByChild("pembuat").startAt(textsearch).endAt(textsearch+"\uf8ff")
            .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listKuis.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    KuisObjek kuisObjek = postSnapshot.getValue(KuisObjek.class);
                    //adding artist to the list
                    listKuis.add(kuisObjek);
                }

                //creating adapter
                Collections.reverse(listKuis);
                KuisLists kuisAdapter = new KuisLists(ListKuisActivity.this , listKuis);
                //attaching adapter to the listview
                listViewkuis.setAdapter(kuisAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        refKuis.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listKuis.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    KuisObjek kuisObjek = postSnapshot.getValue(KuisObjek.class);
                    //adding artist to the list
                    listKuis.add(kuisObjek);
                }

                //creating adapter
                KuisLists kuisAdapter = new KuisLists(ListKuisActivity.this , listKuis);
                //attaching adapter to the listview
                listViewkuis.setAdapter(kuisAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String get_pref(String nama_file, String nama_variable){
        SharedPreferences pref = getSharedPreferences(nama_file,MODE_PRIVATE);
        String value = pref.getString(nama_variable, null);
        return value;
    }

    private void showDialogInsertPass(final KuisObjek kuisObjek) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_inputpass, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextPass);
        final Button buttonpass = (Button) dialogView.findViewById(R.id.buttonInsertPass);
        final Button buttonleaderBoard = (Button) dialogView.findViewById(R.id.buttoncekScore);

        if (kuisObjek.getPembuat().equals(muser))buttonpass.setVisibility(View.GONE);

        dialogBuilder.setTitle(kuisObjek.getNamaKuis());
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextName.getText().toString().equals(kuisObjek.getKode())) {
                    Intent intent = new Intent(getApplicationContext(), JawabKuisActivity.class);

                    intent.putExtra(KUIS_ID, kuisObjek.getId());
                    intent.putExtra(NAMA_KUIS, kuisObjek.getNamaKuis());
                    intent.putExtra(ONE_TIME, kuisObjek.getOnetime());

                    startActivity(intent);
                    b.dismiss();
                }else {
                    b.dismiss();
                    Toast.makeText(getApplicationContext(), "Masukkan Kode yang Benar", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonleaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextName.getText().toString().equals(kuisObjek.getKode())) {
                    Intent intent = new Intent(getApplicationContext(), ScoreListActivity.class);

                    intent.putExtra(KUIS_ID, kuisObjek.getId());
//                    intent.putExtra(NAMA_KUIS, kuisObjek.getNamaKuis());

                    startActivity(intent);
                    b.dismiss();
                }
            }
        });
    }

}
