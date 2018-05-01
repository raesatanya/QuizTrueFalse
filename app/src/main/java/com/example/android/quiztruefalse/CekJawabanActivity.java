package com.example.android.quiztruefalse;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.quiztruefalse.kelasobjek.PertanyaanObjek;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.android.quiztruefalse.BuatTestKuis.REF_SOAL;
import static com.example.android.quiztruefalse.JawabKuisActivity.ARRAYLISTSOAL;
import static com.example.android.quiztruefalse.JawabKuisActivity.SOALBENAR;
import static com.example.android.quiztruefalse.ListKuisActivity.KUIS_ID;
import static com.example.android.quiztruefalse.ListKuisActivity.NAMA_KUIS;

public class CekJawabanActivity extends AppCompatActivity {

    public static final String INT_JUMLAHSOAL = "jumlahsoal";
    public static final String INT_BENAR = "benar";
    public static final String STR_IDKUIS = "idkuis";
    RadioGroup mpilihanGanda;
    TextView mpertanyaan;
    RadioButton mpilihan1,mpilihan2,mpilihan3,mpilihan4;
    Button msoalPrev, msoalNext, msoalSelesai;
    DonutProgress donutProgress;
    Spinner spinner;

    ArrayList<PertanyaanObjek > listPertanyaan;
    String[] jawabanSoal;
    String[] jawabanUser;

    //    constant
    private String PERTANYAAN_CHILD = "pertanyaan";
    private String TEST_CHILD = "testKuis";
    // firbase instance
    DatabaseReference mFirebaseReference;
    DatabaseReference refKuis;

    private String idKuis;

    int iterasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_jawaban);

        Intent i = getIntent();
        idKuis = i.getStringExtra(KUIS_ID);
        listPertanyaan = (ArrayList<PertanyaanObjek>) i.getSerializableExtra(ARRAYLISTSOAL);
        jawabanSoal = new String[listPertanyaan.size()];
        jawabanUser = i.getStringArrayExtra(SOALBENAR);
        int soal = 0;
        for (PertanyaanObjek p : listPertanyaan){
            jawabanSoal[soal++] = p.getJawaban();
        }


        mpilihanGanda = findViewById(R.id.pilihanGanda);
        mpilihanGanda.setClickable(false);

        mpertanyaan = findViewById(R.id.textPertanyaan);
        mpilihan1 = (RadioButton) findViewById(R.id.Pilihan1);
        mpilihan2 = findViewById(R.id.Pilihan2);
        mpilihan3 = findViewById(R.id.Pilihan3);
        mpilihan4 = findViewById(R.id.Pilihan4);
        spinner = findViewById(R.id.spinnerjumlahkuis2);
        showSpinner(listPertanyaan.size());

        //hilangkan.
        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        msoalSelesai = findViewById(R.id.btn_selesai);
        donutProgress.setVisibility(View.GONE);
        msoalSelesai.setVisibility(View.GONE);


//        set reference firebase
        mFirebaseReference = FirebaseDatabase.getInstance().getReference();
        refKuis = mFirebaseReference.child(REF_SOAL).child(idKuis); //ref_soal = soalKuis

        msoalNext = findViewById(R.id.btn_next);
        msoalPrev = findViewById(R.id.btn_before);

        iterasi = 0;
        updateSoal(0,0);

        msoalNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSoal(1, -1);
            }
        });
        msoalPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSoal(-1, -1);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String soal = String.valueOf(adapterView.getItemAtPosition(i)).split(" ")[1];
                updateSoal(0, (Integer.parseInt(soal))-1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    public void updateSoal(int iter, int nomorSoal) {
        if (nomorSoal == -1){
            iterasi += iter;
        }else iterasi = nomorSoal;

        if (iterasi < listPertanyaan.size()-1 && iterasi!=0){
            msoalNext.setVisibility(View.VISIBLE);
            msoalPrev.setVisibility(View.VISIBLE);
        }
        else if (iterasi == 0){
            msoalPrev.setVisibility(View.INVISIBLE);
        }else {
            msoalNext.setVisibility(View.INVISIBLE);
            msoalPrev.setVisibility(View.VISIBLE);
        }

        PertanyaanObjek pertanyaanObjek = listPertanyaan.get(iterasi);

        mpertanyaan.setText(pertanyaanObjek.getPertanyaan());
        mpilihan1.setText(pertanyaanObjek.getPilihanA());
        mpilihan2.setText(pertanyaanObjek.getPilihanB());
        mpilihan3.setText(pertanyaanObjek.getPilihanC());
        mpilihan4.setText(pertanyaanObjek.getPilihanD());

        spinner.setSelection(iterasi);
        ClearRadio(iterasi);
    }

    private void showSpinner(int jumlah) {
        String[] list = new String[jumlah];
        for (int i = 0; i<jumlah;i++){
            list[i] = "Soal "+String.valueOf(i+1);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }


    private void ClearRadio( int index) {

        mpilihan1.setBackgroundColor(Color.WHITE);
        mpilihan2.setBackgroundColor(Color.WHITE);
        mpilihan3.setBackgroundColor(Color.WHITE);
        mpilihan4.setBackgroundColor(Color.WHITE);

        if (TextUtils.equals(jawabanSoal[index],jawabanUser[index])) {
            switch (jawabanUser[index]) {
                case "A":
                    mpilihan1.setChecked(true);
                    mpilihan1.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                case "B":
                    mpilihan2.setChecked(true);
                    mpilihan2.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                case "C":
                    mpilihan3.setChecked(true);
                    mpilihan3.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                case "D":
                    mpilihan4.setChecked(true);
                    mpilihan4.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
            }
        }else {
            switch (jawabanUser[index]) {
                case "A":
                    mpilihan1.setChecked(true);
                    mpilihan1.setBackgroundColor(getResources().getColor(R.color.red));
                    break;
                case "B":
                    mpilihan2.setChecked(true);
                    mpilihan2.setBackgroundColor(getResources().getColor(R.color.red));
                    break;
                case "C":
                    mpilihan3.setChecked(true);
                    mpilihan3.setBackgroundColor(getResources().getColor(R.color.red));
                    break;
                case "D":
                    mpilihan4.setChecked(true);
                    mpilihan4.setBackgroundColor(getResources().getColor(R.color.red));
                    break;
            }switch (jawabanSoal[index]) {
                case "A":
                    mpilihan1.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                case "B":
                    mpilihan2.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                case "C":
                    mpilihan3.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                case "D":
                    mpilihan4.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
            }
        }
    }
}
