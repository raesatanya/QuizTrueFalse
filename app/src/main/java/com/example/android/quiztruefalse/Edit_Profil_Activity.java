package com.example.android.quiztruefalse;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;


public class Edit_Profil_Activity extends AppCompatActivity {


    TextView nama_editprof, email_editprof,pass_editprof;
    private ImageView imageView;
    Context mContext;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;
    sharePrefer Pref;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    String a,b,c;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profil_);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Pref = new sharePrefer(Edit_Profil_Activity.this);
        mAuth = FirebaseAuth.getInstance();
        //nama_editprof =(EditText)findViewById(R.id.nama_editprofil);
        email_editprof =(TextView) findViewById(R.id.email_edit);
        pass_editprof =(TextView) findViewById(R.id.pass_editProf);
        imageView = (ImageView)findViewById(R.id.image_profil);
        nama_editprof =(TextView) findViewById(R.id.nama_display_editprof);
        progressBar = (ProgressBar) findViewById(R.id.prgresbar_gambar);

        progressBar.setVisibility(View.GONE);
        progressBar.bringToFront();
        progres_dialog();
        settoEditText();
        get_foto();
        get_data_user();



    }
    void get_data_user(){
        String email =  mAuth.getCurrentUser().getEmail().trim().toString();
        try {
            String nama=mAuth.getCurrentUser().getDisplayName().trim().toString();
            nama_editprof.setText(nama);

        }catch (Exception e){

        }

        email_editprof.setText(email);
    }


    public void settoEditText(){
        // email_editprof.setText(get_pref("user","email"));
        // pass_editprof.setText(get_pref("user","password"));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }





    public void simpan(View view) {
        //final int temp = Integer.parseInt(get_pref("user","id_user"));
        //Pref.get_pref("user","uid");
        uploadImage();


    }

    public void batalkan(View view) {
        finish();
    }

    public void singout(View view) {
        // remove_pref("user");
        Intent intent = new Intent(Edit_Profil_Activity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



    public void ganti_foto(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
    private void uploadImage() {
        String status_foto =Pref.get_pref("user","foto");
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            // FirebaseStorage.getInstance().getReference().child("images_profil/").child(Pref.get_pref("user", "uid")).delete();
            StorageReference ref = storageReference.child("images_profil/").child(Pref.get_pref("user","uid"));
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(Edit_Profil_Activity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Edit_Profil_Activity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            if(progress==100){
                                Pref.simple_savePrefer("user","foto","true");

                            }
                        }
                    });


        }


    }
    void get_foto(){
        String uid =Pref.get_pref("user","uid");
        progressBar.setVisibility(View.VISIBLE);
        storageReference.child("images_profil/"+uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                if(uri==null){
                    Toast.makeText(Edit_Profil_Activity.this, "Failed ", Toast.LENGTH_SHORT).show();


                }else {

                    progressBar.setVisibility(View.GONE);
                    Log.d("string uri",uri.toString());
                    Glide.with(Edit_Profil_Activity.this).load(uri).override(100,100).into(imageView);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(Edit_Profil_Activity.this, "Failed "+exception.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

/*

        Glide.with(Edit_Profil_Activity.this )
                .using(new FirebaseImageLoader())
                .load(uri)
                .listener(new RequestListener<StorageReference, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // progressBar.setVisibility(View.GONE);

                        return false;
                    }

                })
                .into(imageView );







    //           StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images_profil/"+uid+".jpg");

*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        //get_foto();
    }
    void re_autpassword(){
        progres_dialog();
        progressDialog.show();
        final FirebaseUser userU;
        userU = FirebaseAuth.getInstance().getCurrentUser();
        String  email =mAuth.getCurrentUser().getEmail().trim();
        AuthCredential credential = EmailAuthProvider.getCredential(email,getA());

        userU.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    userU.updatePassword(getB()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Edit_Profil_Activity.this, "gagal Update Password", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }else {
                                Toast.makeText(Edit_Profil_Activity.this, "password telah di perbahuri", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                        }
                    });
                }else {

                    Toast.makeText(Edit_Profil_Activity.this, "password anda salah ", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }
        });
        progressDialog.dismiss();
    }
    void dialog_password (){
        LayoutInflater factory = LayoutInflater.from(this);

//text_entry is an Layout XML file containing two text field to display in alert dialog
        final View textEntryView = factory.inflate(R.layout.alert_password, null);

        final EditText oldpass = (EditText) textEntryView.findViewById(R.id.etpassword_lama);
        final EditText pass = (EditText) textEntryView.findViewById(R.id.etPassword_baru);
        final EditText repass = (EditText) textEntryView.findViewById(R.id.etRePassword_baru);


        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Ubah Password").setView(textEntryView).setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        String old = oldpass.getText().toString();
                        String ne = pass.getText().toString();
                        pidah_passwor(old,ne);
                        re_autpassword();

                        /* User clicked OK so do some stuff */
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        /*
                         * User clicked cancel so do some stuff
                         */
                    }
                });
        alert.show();
    }

    public void ganti_password(View view) {
        dialog_password();
    }

    void dialog_email(){
        LayoutInflater factory = LayoutInflater.from(this);

//text_entry is an Layout XML file containing two text field to display in alert dialog
        final View textEntryView = factory.inflate(R.layout.alert_email, null);

        final EditText input1 = (EditText) textEntryView.findViewById(R.id.etemail_baru);


        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Ubah Email Anda").setView(textEntryView).setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        setC(input1.getText().toString());
                        update_email();
                        get_data_user();

                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        /*
                         * User clicked cancel so do some stuff
                         */
                    }
                });
        alert.show();

    }
    void update_email(){
        progressDialog.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updateEmail(getC())
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(Edit_Profil_Activity.this, "Update email berhasil", Toast.LENGTH_SHORT).show();
                            get_data_user();


                        }else{
                            Toast.makeText(Edit_Profil_Activity.this, "Update email gagal", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
        progressDialog.dismiss();
    }

    public void ganti_email(View view) {
        dialog_email();
    }
    void pidah_passwor(String a, String b ){
        this.a=a;
        this.b=b;
        this.c=c;
    }

    String getA() {
        return a;
    }

    String getB() {
        return b;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getC() {
        return c;
    }

    public void ganti_displayname(View view) {
        dialog_nama();

    }
    void dialog_nama(){
        progres_dialog();
        progressDialog.show();
        LayoutInflater factory = LayoutInflater.from(this);

//text_entry is an Layout XML file containing two text field to display in alert dialog
        final View textEntryView = factory.inflate(R.layout.alert_nama, null);

        final EditText Nama = (EditText) textEntryView.findViewById(R.id.etnama_display);



        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Ubah Nama  Anda").setView(textEntryView).setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(Nama.getText().toString())
                                .build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(Edit_Profil_Activity.this, "Update Profil berhasil", Toast.LENGTH_SHORT).show();
                                            nama_editprof.setText(Nama.getText().toString());
                                            //Pref.get_pref("")

                                        }else{
                                            progressDialog.dismiss();
                                            Toast.makeText(Edit_Profil_Activity.this, "Update Profil gagal", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });

                        /* User clicked OK so do some stuff */
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        /*
                         * User clicked cancel so do some stuff
                         */
                    }
                });
        alert.show();

    }
    void progres_dialog(){
        progressDialog = new ProgressDialog(Edit_Profil_Activity.this);
        progressDialog.setMessage("Please Wait..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner

        //  progressDialog.setCancelable(false);
    }


}


