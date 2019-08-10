package com.codeelit.ts.Learn;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.codeelit.ts.Dummy;
import com.codeelit.ts.Fragments.CompaniesFragment;
import com.codeelit.ts.Fragments.DiscussionFragment;
import com.codeelit.ts.Fragments.ProfileFragment;
import com.codeelit.ts.Practice.PracticeFragment;
import com.codeelit.ts.R;
import com.ramotion.foldingcell.FoldingCell;
import java.util.HashSet;
import java.util.List;


public class FoldingCellListAdapter extends ArrayAdapter<AllLearnItem> {
    private AllLearnItem Chapter;
    FoldingCell cell;
    private OnClickListener defaultRequestBtnClickListener;
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();

    private static class ViewHolder {
        TextView Description;
        TextView Difficulty;
        TextView cellTitle;
        TextView contentRequestBtn;
        ImageView headerImage;
        ImageView imageCell;
        TextView pre;
        TextView titleDificulty;
        CardView view;

        private ViewHolder() {
        }
    }

    public FoldingCellListAdapter(Context context, List<AllLearnItem> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        this.cell = (FoldingCell) convertView;
        if (this.cell == null) {
            viewHolder = new ViewHolder();
            this.cell = (FoldingCell) LayoutInflater.from(getContext()).inflate(R.layout.cell, parent, false);
            this.Chapter = (AllLearnItem) getItem(position);
            viewHolder.Description = (TextView) this.cell.findViewById(R.id.Chapter_Description);
            viewHolder.contentRequestBtn = (TextView) this.cell.findViewById(R.id.content_request_btn);
            viewHolder.Difficulty = (TextView) this.cell.findViewById(R.id.course_difficulty);
            viewHolder.pre = (TextView) this.cell.findViewById(R.id.course_pre);
            viewHolder.headerImage = (ImageView) this.cell.findViewById(R.id.head_image);
            viewHolder.cellTitle = (TextView) this.cell.findViewById(R.id.Title);
            viewHolder.imageCell = (ImageView) this.cell.findViewById(R.id.recycler_image);
            viewHolder.view = (CardView) this.cell.findViewById(R.id.root_of_conetent);
            viewHolder.titleDificulty = (TextView) this.cell.findViewById(R.id.title_to_address);
            viewHolder.Description.setText(this.Chapter.getCDescription());
            viewHolder.cellTitle.setText(this.Chapter.getCourseTitle());
            viewHolder.Difficulty.setText(this.Chapter.getDificulty());
            viewHolder.titleDificulty.setText(this.Chapter.getDificulty());
            viewHolder.pre.setText(this.Chapter.getPre());

            this.cell.setTag(viewHolder);
        } else {
            if (this.unfoldedIndexes.contains(Integer.valueOf(position))) {
                this.cell.unfold(true);
            } else {
                this.cell.fold(true);
            }
            viewHolder = (ViewHolder) this.cell.getTag();
        }
        switch (position) {
            case 0:
                viewHolder.contentRequestBtn.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        FoldingCellListAdapter.this.getContext().startActivity(new Intent(FoldingCellListAdapter.this.getContext(), Dummy.class));
                    }
                });
                break;
            case 1:
                viewHolder.contentRequestBtn.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        FoldingCellListAdapter.this.getContext().startActivity(new Intent(FoldingCellListAdapter.this.getContext(), PracticeFragment.class));
                    }
                });
                break;
            case 2:
                viewHolder.contentRequestBtn.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        FoldingCellListAdapter.this.getContext().startActivity(new Intent(FoldingCellListAdapter.this.getContext(), CompaniesFragment.class));
                    }
                });
                break;
            case 3:
                viewHolder.contentRequestBtn.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        FoldingCellListAdapter.this.getContext().startActivity(new Intent(FoldingCellListAdapter.this.getContext(), DiscussionFragment.class));
                    }
                });
                break;
            case 4:
                viewHolder.contentRequestBtn.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        FoldingCellListAdapter.this.getContext().startActivity(new Intent(FoldingCellListAdapter.this.getContext(), ProfileFragment.class));
                    }
                });
                break;
        }
        return this.cell;
    }

    public void registerToggle(int position) {
        if (this.unfoldedIndexes.contains(Integer.valueOf(position))) {
            registerFold(position);
        } else {
            registerUnfold(position);
        }
    }

    public void registerFold(int position) {
        this.unfoldedIndexes.remove(Integer.valueOf(position));
    }

    public void registerUnfold(int position) {
        this.unfoldedIndexes.add(Integer.valueOf(position));
    }
}
