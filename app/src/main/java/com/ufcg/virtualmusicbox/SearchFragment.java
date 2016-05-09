package com.ufcg.virtualmusicbox;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.ufcg.virtualmusicbox.Model.Music;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by st on 06/05/2016.
 */
public class SearchFragment extends Fragment {

    private RecyclerView rv;
    private ArrayList<Music> play;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void update() {
        Log.e("UPDATEEEEEEE", "VAAAAAAAAAI");
        play = MainActivity.getmFilterPlayList();

        for(Music i:play) Log.e("FILTERES LIST ", " CAGADAAAA" + i.getTitulo());

        MyAdapter adapter = new MyAdapter(play);
        rv.setAdapter(adapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("UPDATEEEEEEE", "fora do if");
        if (isVisibleToUser) {
            Log.e("UPDATEEEEEEE", "VAAAAAAAAAI");
            update();
        } else {
            Log.e("UPDATEEEEEEE", "else");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);
//        Log.d("Tag", data);

        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        play = MainActivity.getmFilterPlayList();

        MyAdapter adapter = new MyAdapter(play);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

}