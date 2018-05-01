package com.example.android.quiztruefalse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BerandaActivity extends AppCompatActivity {

    private static final String SHARED =  "com.example.bangijan69.myapplication";
    TextView tvResultNama;
    String resultNama , resultEmail, resultPass;
    int reslutId;
    Context mContext;
    String userName;

    private FirebaseAuth mauth;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);
        mContext = this;;
        mauth = FirebaseAuth.getInstance();
        firebaseUser = mauth.getCurrentUser();

    }

    @Override
    protected void onStart() {
        super.onStart();
//        Intent i = getIntent();
//        userName = i.getStringExtra("user");
//        if (!TextUtils.isEmpty(userName)){
//            finish();
//        }
    }

    public void btn_edit_profil(View view) {
        Intent intent = new Intent(mContext, Edit_Profil_Activity.class);

        startActivity(intent);

    }

    public void buat_kuis(View view) {
        Intent intent = new Intent(mContext, Buat_kuis_Activity.class);

        startActivity(intent);

    }

    public void kuis_list(View view) {
        Intent intent = new Intent(mContext, ListKuisActivity.class);
        //intent.putExtra("result_id",getReslutId());
        startActivity(intent);
    }

    public String get_pref(String nama_file, String nama_variable){
        SharedPreferences pref = getSharedPreferences(nama_file,MODE_PRIVATE);
        String value = pref.getString(nama_variable, null);
        return value;
    }

    public void signout(View view) {
        SharedPreferences pref = getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor edit_pref = pref.edit();
        edit_pref.clear();
        edit_pref.commit();
        mauth.signOut();
        Intent intent = new Intent(BerandaActivity.this, LoginActivity.class);
        finish();
        startActivity(intent);

    }

    public void cek_leaderboard(View view) {
        Intent intent = new Intent(mContext, UserScoreActivity.class);
        //intent.putExtra("result_id",getReslutId());
        startActivity(intent);
    }

    public void about_us(View view) {
        Intent intent = new Intent(mContext, AboutUsActivity.class);
        startActivity(intent);
    }
}
