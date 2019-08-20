package com.codeelit.ts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



import java.util.ArrayList;


public class ListAdapter extends ArrayAdapter<String> {

    private ArrayList<String> objects;

    public ListAdapter(Context context, int textViewResourceId, ArrayList<String> qUESTIONS)
    {
        super(context, textViewResourceId, qUESTIONS);
        objects = qUESTIONS;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;


            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.correct_list, null);

            TextView txtqstn = (TextView) v.findViewById(R.id.correct_list_answer);


            txtqstn.setText("" + objects.get(position).getBytes().toString());


        return v;
    }
}
