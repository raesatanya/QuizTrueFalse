package com.example.android.quiztruefalse;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bangijan69 on 4/6/2018.
 */

public class sharePrefer {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedprefEditor;
    public String PREF_NAME ;

    sharePrefer(Context context){
        this.context=context;
    }

    public void save_praf(String nama_file, String nama_variable,String values, String nama_variable2, String values2, String nama_varibael3, String values3,String nama_variable4, String values4){
        SharedPreferences pref = context.getSharedPreferences(nama_file, MODE_PRIVATE);
        if(nama_variable2.equals(null) ){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(nama_variable,values);
            editor.commit();

        }if (nama_varibael3.equals(null)){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(nama_variable,values);
            editor.putString(nama_variable2,values2);
            editor.commit();
        }if(nama_variable4 == null){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(nama_variable,values);
            editor.putString(nama_variable2,values2);
            editor.putString(nama_varibael3,values3);
            editor.commit();
        }
        else{
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(nama_variable,values);
            editor.putString(nama_variable2,values2);
            editor.putString(nama_varibael3,values3);
            editor.putString(nama_variable4,values4);
            editor.commit();
        }


    }
    void simple_savePrefer(String nama_file, String nama_variable, String values){
        SharedPreferences pref = context.getSharedPreferences(nama_file, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(nama_variable,values);
        editor.commit();

    }
    public String get_pref(String nama_file, String nama_variable){
        SharedPreferences pref = context.getSharedPreferences(nama_file,MODE_PRIVATE);
        String value = pref.getString(nama_variable, null);
        return value;
    }
    public void remove_pref(String nama_file){
        SharedPreferences pref = context.getSharedPreferences(nama_file, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

}
