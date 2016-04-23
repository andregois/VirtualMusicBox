package com.ufcg.virtualmusicbox;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Music> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView;
        public ImageView mImageView;
        public CheckBox mCheck;
        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextView = (TextView) v.findViewById(R.id.tv_text);
            mImageView = (ImageView) v.findViewById(R.id.iv_image);
            mCheck = (CheckBox) v.findViewById(R.id.cb_voting);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Music> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!holder.mCheck.isChecked()){
                    mDataset.get(position).votos--;
                    MainActivity.setVoted(mDataset.get(position), false);
                    holder.mTextView.setText(mDataset.get(position).cantor + " - "+ mDataset.get(position).titulo +" - " + (mDataset.get(position).votos +""));


                }else {
                    mDataset.get(position).votos++;
                    MainActivity.setVoted(mDataset.get(position), true);
                    holder.mTextView.setText(mDataset.get(position).cantor + " - "+ mDataset.get(position).titulo +" - " + (mDataset.get(position).votos +""));
                }
//                Log.d("chamou o setcg","");
//                notifyDataSetChanged();
            }
        });

        holder.mTextView.setText(mDataset.get(position).cantor + " - "+mDataset.get(position).titulo +" - " + (mDataset.get(position).votos +""));
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}