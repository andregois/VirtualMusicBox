package com.ufcg.virtualmusicbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TwoFragment extends Fragment {
    private RecyclerView rv;

//    private Bundle bundle;
//    private String data;
    private ArrayList<Music> play;

    public TwoFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        bundle = getArguments();
//        data = bundle.getString("LIST");
    }

    public void update(){
        play = MainActivity.getRank();

//        Log.d("dois update",play.get(0).titulo);
        RankingAdapter adapter = new RankingAdapter(play);
        rv.setAdapter(adapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            update();
        } else {

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);
//        Log.d("Tag", data);


//        Gson gson = new Gson();
//        Music[] arr = gson.fromJson(data, Music[].class);
//        for (Music m : arr) {
//            Log.d("cantor: ", m.cantor);
//            Log.d("titulo: ", m.titulo);
//            Log.d("votos: ", m.votos + "");
//        }
        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        play = MainActivity.getRank();

        RankingAdapter adapter = new RankingAdapter(play);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

}