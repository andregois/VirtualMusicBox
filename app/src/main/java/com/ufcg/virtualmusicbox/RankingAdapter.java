package com.ufcg.virtualmusicbox;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ufcg.virtualmusicbox.Model.Changeable;
import com.ufcg.virtualmusicbox.Model.Music;

import java.util.ArrayList;
import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> implements Changeable {
    private ArrayList<Music> mDataset;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTitle;
        public TextView mSinger;
        public TextView mVotes;
        public CheckBox mCheck;
        public View mView;

        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTitle = (TextView) v.findViewById(R.id.tv_title);
            mSinger = (TextView) v.findViewById(R.id.tv_singer);
            mVotes = (TextView) v.findViewById(R.id.tv_votes);
            mCheck = (CheckBox) v.findViewById(R.id.cb_voting);
            mView = v;

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RankingAdapter(ArrayList<Music> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RankingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.mCheck.setClickable(false);
        holder.mCheck.setChecked(true);
        holder.mTitle.setText(mDataset.get(position).titulo);
        holder.mSinger.setText(mDataset.get(position).cantor);
        holder.mVotes.setText("" + mDataset.get(position).votos);
    }

    @Override
    public int getItemCount() {
        if (mDataset == null) return 0;
        return mDataset.size();
    }

    @Override
    public List<Music> getmDataset() {
        return mDataset;
    }

    public void add(int position, Music item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Music item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void removeAll() {
        mDataset.clear();
        notifyDataSetChanged();
    }
}