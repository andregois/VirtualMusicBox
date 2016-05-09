package com.ufcg.virtualmusicbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufcg.virtualmusicbox.Model.Changeable;
import com.ufcg.virtualmusicbox.Model.Music;
import com.ufcg.virtualmusicbox.Model.Searchable;

import java.util.ArrayList;


public class RankingFragment extends Fragment implements Searchable {
    private RecyclerView rv;
    private RankingAdapter adapter;

    //    private Bundle bundle;
//    private String data;
    private ArrayList<Music> play;

    public RankingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RankingAdapter(play);
//        bundle = getArguments();
//        data = bundle.getString("LIST");
    }

    public void update() {
        Log.e("RANKING", "UPDATE");
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


        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

    @Override
    public Changeable getRecyclerAdapter() {
        return adapter;
    }
}