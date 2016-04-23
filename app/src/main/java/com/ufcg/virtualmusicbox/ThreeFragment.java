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


public class ThreeFragment extends Fragment {
    //    private Bundle bundle;
    private RecyclerView rv;

//    private String data;

    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        bundle = getArguments();
//        data = bundle.getString("LIST");

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            update();
        } else {

        }
    }

    public void update() {
        RankingAdapter adapter = new RankingAdapter(MainActivity.getVoted());
        rv.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

//        Log.d("Tag", data);
//
//
//        Gson gson = new Gson();
//        Music[] arr = gson.fromJson(data, Music[].class);
//        for (Music m : arr) {
//            Log.d("cantor: ", m.cantor);
//            Log.d("titulo: ", m.titulo);
//            Log.d("votos: ", m.votos + "");
//        }
        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter(MainActivity.getVoted());
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        return rootView;
    }

}
