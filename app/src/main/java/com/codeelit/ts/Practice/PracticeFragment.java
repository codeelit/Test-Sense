package com.codeelit.ts.Practice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeelit.ts.Learn.AllLearnItem;
import com.codeelit.ts.Learn.FoldingCellRecyclerAdapter;
import com.codeelit.ts.R;

public class PracticeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    public static PracticeFragment newInstance(String param1, String param2) {
        PracticeFragment fragment = new PracticeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewItem = inflater.inflate(R.layout.fragment_learn, container, false);
        RecyclerView theListView = (RecyclerView) viewItem.findViewById(R.id.mainListView);
        theListView.setLayoutManager(new LinearLayoutManager(getContext()));
        theListView.setAdapter(new FoldingCellRecyclerAdapter(getContext(), AllLearnItem.getTestingList()));
        return viewItem;
    }
}
