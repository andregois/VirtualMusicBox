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


public class VotingFragment extends Fragment implements Searchable{
//    private Bundle bundle;
//    private String data;
    private RecyclerView rv;
    MyAdapter adapter;

    public VotingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        adapter = new MyAdapter(MainActivity.getmPlayList());
        adapter.notifyDataSetChanged();



//        bundle = getArguments();
//        data = bundle.getString("LIST");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);


//        Log.d("Tag", data);
//
//        Gson gson = new Gson();
//        Music[] arr = gson.fromJson(data, Music[].class);
//        for (Music m : arr) {
//            Log.d("cantor: ", m.cantor);
//            Log.d("titulo: ", m.titulo)s;
//            Log.d("votos: ", m.votos + "");
//
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
