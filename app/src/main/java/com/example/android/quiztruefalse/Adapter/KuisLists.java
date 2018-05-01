package com.example.android.quiztruefalse.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.quiztruefalse.R;
import com.example.android.quiztruefalse.kelasobjek.KuisObjek;

import java.util.ArrayList;

/**
 * Created by Aulia Ramadhan on 13/04/2018.
 */

public class KuisLists extends ArrayAdapter<KuisObjek>{

    private Activity context;
    ArrayList<KuisObjek> listKuis;

    public KuisLists(Activity context, ArrayList<KuisObjek> artists) {
        super(context, R.layout.layout_kuis_list, artists);
        this.context = context;
        this.listKuis = artists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_kuis_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textName);
        TextView textViewUser = (TextView) listViewItem.findViewById(R.id.textUser);

        KuisObjek kuisObjek = listKuis.get(position);
        textViewName.setText(kuisObjek.getNamaKuis());
        textViewUser.setText(kuisObjek.getPembuat());

        return listViewItem;
    }


}
