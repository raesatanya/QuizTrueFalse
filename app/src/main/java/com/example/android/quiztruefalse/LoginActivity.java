package com.example.android.quiztruefalse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.SharedPreferences.*;

public class LoginActivity extends AppCompatActivity {
    private static final String SHARED =  "com.example.android.quiztruefalse";
    EditText etEmail;
    EditText etPassword;
    TextView textView_alert;
    Button btnLogin;
    ProgressDialog progressDialog;
    Context mContext;
    String email, password;
    sharePrefer  Pref;
    private FirebaseAuth mAuth;
    DatabaseReference databaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        Pref = new sharePrefer(LoginActivity.this);
        databaseUser = FirebaseDatabase.getInstance().getReference("user");
        dialog_progres();

        mContext = this;
        textView_alert = (TextView)findViewById(R.id.alert_login);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mengambil data dan merubah ke type data string
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();

                //Melakukan if untuk memvalidasi ketika login
                if (validateForm(email,password)) {
                    signIn(email, password);
                } else {
                    //Menampilkan toast ketika salah
                    Toast.makeText(LoginActivity.this, "Please Fill the Form",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        //findViewById dari button



    }
    //Membuat metode onStart()
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser(); //mengecek masih login ga ?
        if ( Pref.get_pref("user","uid")!= null) {
            sendToMain();
        }
    }
    //Metod untuk menamahkan akun


    private void signIn(String email, String password) {
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            // tombol signIn di tap, maka akan pindah ke laman aktivitas utama
                            progressDialog.dismiss();
                            Pref.simple_savePrefer("user", "uid",mAuth.getCurrentUser().getUid());
                            Pref.simple_savePrefer("user", "email_user",mAuth.getCurrentUser().getEmail());
                            Pref.simple_savePrefer("user", "foto","false");

                            Intent i = new Intent(LoginActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            //jika salah maka akan menampilkan toast
                            progressDialog.dismiss();
                            textView_alert.setText("Anda  belum terdaftar");

                        }
                    }
                });

    }
    //Metode bolean untuk memvalidasi
    private boolean validateForm(String emial, String password) {
        // kondisi ketika email dan password kosong
        if (emial.isEmpty() || password.isEmpty() ) {
            textView_alert.setText("Username Dan password  anda kosong");
            return false;
        }if(emial.indexOf("@")==-1){
            textView_alert.setText("Email anda tidak valid");
            return false;
        }

        else {
            return true;
        }
    }
    //metod Untuk memindahkan laman
    private void sendToMain() {
        SharedPreferences preferences = getSharedPreferences(SHARED, MODE_PRIVATE);
        Editor edit = preferences.edit();
        edit.putString("user",mAuth.getCurrentUser().getEmail().split("@")[0] );
        edit.commit();
        Intent mainIntent = new Intent(LoginActivity.this, BerandaActivity.class);
        startActivity(mainIntent);
        finish();

    }

    public void to_register(View view) {
        Intent a = new Intent(LoginActivity.this,RegistrasiActivity.class);
        startActivity(a);
        finish();
    }
    void  dialog_progres(){
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner

    }


}




