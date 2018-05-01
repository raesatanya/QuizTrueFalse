package com.example.android.quiztruefalse.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.quiztruefalse.R;
import com.example.android.quiztruefalse.kelasobjek.KuisObjek;
import com.example.android.quiztruefalse.kelasobjek.ScoreObjek;

import java.util.ArrayList;

/**
 * Created by Aulia Ramadhan on 24/04/2018.
 */

public class ScoreLists extends ArrayAdapter<ScoreObjek>{


    private Activity context;
    ArrayList<ScoreObjek> listScore;

    public ScoreLists(Activity context, ArrayList<ScoreObjek> artists) {
        super(context, R.layout.layout_kuis_list, artists);
        this.context = context;
        this.listScore = artists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_kuis_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textName);
        TextView textViewUser = (TextView) listViewItem.findViewById(R.id.textUser);
        TextView textViewScore = listViewItem.findViewById(R.id.textScore);

//        RelativeLayout r = (RelativeLayout) listViewItem.findViewById(R.id.listLayout);
//        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT
//        );
//
//        layout.setMargins(0,10,0,10);
//        r.setLayoutParams(layout);

        textViewUser.setVisibility(View.GONE);

        ScoreObjek scoreObjek = listScore.get(position);
        textViewName.setText(scoreObjek.getNamaUser());
        textViewScore.setText(String.valueOf(scoreObjek.getScore()));

        return listViewItem;
    }


}