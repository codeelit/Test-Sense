package com.codeelit.ts.Learn;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codeelit.ts.Dummy;
import com.codeelit.ts.Fragments.CompaniesFragment;
import com.codeelit.ts.Fragments.DiscussionFragment;
import com.codeelit.ts.Fragments.ProfileFragment;
import com.codeelit.ts.Practice.PracticeFragment;
import com.codeelit.ts.R;
import com.ramotion.foldingcell.FoldingCell;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class FoldingCellRecyclerAdapter extends RecyclerView.Adapter<FoldingCellRecyclerAdapter.foldingHolder> {
    private AllLearnItem Chapter;
    private List<AllLearnItem> items;
    Context mContext;
    /* access modifiers changed from: private */
    public HashSet<Integer> unfoldedIndexes = new HashSet<>();

    class foldingHolder extends RecyclerView.ViewHolder implements OnClickListener {
        TextView Description;
        TextView Difficulty;
        FoldingCell cell;
        TextView cellTitle;
        TextView contentRequestBtn;
        ImageView headerImage;
        ImageView imageCell;
        TextView pre;
        TextView titleDificulty;
        CardView view;

        public foldingHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            if (this.cell == null) {
                this.cell = (FoldingCell) itemView;
                this.Description = (TextView) itemView.findViewById(R.id.Chapter_Description);
                this.contentRequestBtn = (TextView) itemView.findViewById(R.id.content_request_btn);
                this.Difficulty = (TextView) itemView.findViewById(R.id.course_difficulty);
                this.pre = (TextView) itemView.findViewById(R.id.course_pre);
                this.headerImage = (ImageView) itemView.findViewById(R.id.head_image);
                this.cellTitle = (TextView) itemView.findViewById(R.id.Title);
                this.imageCell = (ImageView) itemView.findViewById(R.id.recycler_image);
                this.view = (CardView) itemView.findViewById(R.id.root_of_conetent);
                this.titleDificulty = (TextView) itemView.findViewById(R.id.title_to_address);
            } else if (FoldingCellRecyclerAdapter.this.unfoldedIndexes.contains(Integer.valueOf(getAdapterPosition()))) {
                this.cell.unfold(true);
            } else {
                this.cell.fold(true);
            }
        }

        public void onClick(View view2) {
            ((FoldingCell) view2).toggle(false);
            FoldingCellRecyclerAdapter.this.registerToggle(getAdapterPosition());
        }
    }

    public FoldingCellRecyclerAdapter(Context context, ArrayList<AllLearnItem> objects) {
        this.mContext = context;
        this.items = objects;
    }

    public foldingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new foldingHolder(LayoutInflater.from(this.mContext).inflate(R.layout.cell, parent, false));
    }

    public void onBindViewHolder(foldingHolder viewHolder, int position) {
        this.Chapter = (AllLearnItem) this.items.get(position);
        viewHolder.Description.setText(this.Chapter.getCDescription());
        viewHolder.cellTitle.setText(this.Chapter.getCourseTitle());
        viewHolder.Difficulty.setText(this.Chapter.getDificulty());
        viewHolder.titleDificulty.setText(this.Chapter.getDificulty());
        viewHolder.pre.setText(this.Chapter.getPre());

        switch (position) {
            case 0:
                viewHolder.contentRequestBtn.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        FoldingCellRecyclerAdapter.this.mContext.startActivity(new Intent(FoldingCellRecyclerAdapter.this.mContext, Dummy.class));
                    }
                });
                return;
            case 1:
                viewHolder.contentRequestBtn.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        FoldingCellRecyclerAdapter.this.mContext.startActivity(new Intent(FoldingCellRecyclerAdapter.this.mContext, PracticeFragment.class));
                    }
                });
                return;
            case 2:
                viewHolder.contentRequestBtn.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        FoldingCellRecyclerAdapter.this.mContext.startActivity(new Intent(FoldingCellRecyclerAdapter.this.mContext, CompaniesFragment.class));
                    }
                });
                return;
            case 3:
                viewHolder.contentRequestBtn.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        FoldingCellRecyclerAdapter.this.mContext.startActivity(new Intent(FoldingCellRecyclerAdapter.this.mContext, DiscussionFragment.class));
                    }
                });
                return;
            case 4:
                viewHolder.contentRequestBtn.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        FoldingCellRecyclerAdapter.this.mContext.startActivity(new Intent(FoldingCellRecyclerAdapter.this.mContext, ProfileFragment.class));
                    }
                });
                return;
            default:
                return;
        }
    }

    public int getItemCount() {
        return this.items.size();
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
