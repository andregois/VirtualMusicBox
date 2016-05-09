package com.ufcg.virtualmusicbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufcg.virtualmusicbox.Model.Changeable;
import com.ufcg.virtualmusicbox.Model.Searchable;


public class MyVotesFragment extends Fragment implements Searchable{
    //    private Bundle bundle;
    private RecyclerView rv;
    private MyAdapter adapter;

//    private String data;

    public MyVotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MyAdapter(MainActivity.getVoted());
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
