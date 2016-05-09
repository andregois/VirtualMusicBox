package com.ufcg.virtualmusicbox;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.support.v7.widget.SearchView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ufcg.virtualmusicbox.Model.Changeable;
import com.ufcg.virtualmusicbox.Model.Music;
import com.ufcg.virtualmusicbox.Model.Searchable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static Music[] mArr;
    private static ArrayList<Music> mVoted = new ArrayList<>();
    private static ArrayList<Music> mPlayList = new ArrayList<>();
    private static ArrayList<Music> searchResult = new ArrayList<>();
    private static ArrayList<Music> reset = new ArrayList<>();
    private SearchView searchView;
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    private int fragment_pos;
    private String data;
    SearchFragment frag;

    VotingFragment votingFragment = new VotingFragment();
    RankingFragment rankingFragment = new RankingFragment();
    MyVotesFragment myVotesFragment = new MyVotesFragment();


    public static ArrayList<Music> getVoted() {
        return mVoted;
    }

    public static void setVoted(Music voted, boolean b) {
        if (b) {
            mVoted.add(voted);
        } else {
            mVoted.remove(voted);
        }
    }

    public static ArrayList<Music> getRank() {
        ArrayList<Music> rank = new ArrayList<>();
        for (Music m : mPlayList) {
            rank.add(m);
        }
        Collections.sort(rank);
        return rank;
    }

    public static Music[] getArr() {
        return mArr;
    }

    public static void setArr(Music[] arr) {
        mArr = arr;
    }

    public static ArrayList<Music> getmPlayList() {
        return mPlayList;
    }

    public static void setmPlayList(ArrayList<Music> mPlayList) {
        MainActivity.mPlayList = mPlayList;
    }

    public static ArrayList<Music> getmFilterPlayList() {
        return searchResult;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        String input = "https://api.myjson.com/bins/2vlfm";
        HttpGet http = new HttpGet(input);
        String playList = conectToServer(http);

        try {
            JSONArray json = new JSONArray(playList);
            Log.i("Json recebido: ", String.valueOf(json));
            data = String.valueOf(json);

            Gson gson = new Gson();
            mArr = gson.fromJson(String.valueOf(json), Music[].class);
            mPlayList = gson.fromJson(String.valueOf(json), new TypeToken<ArrayList<Music>>() {
            }.getType());

            reset = new ArrayList<Music>(mPlayList);

//            for (Music m : mPlayList) {
//                Log.d("cantor: ", m.cantor);
//                Log.d("titulo: ", m.titulo);
//                Log.d("votos: ", m.votos + "");
//
//            }
        } catch (Exception e) {
            Log.e("JSON Parser", "Error parsing data:" + e.toString());
            e.printStackTrace();
        }


        votingFragment = new VotingFragment();
        rankingFragment = new RankingFragment();
        myVotesFragment = new MyVotesFragment();


    }

    public String conectToServer(HttpGet httpGet) {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
//                    Log.d("Line", line);
                }
            } else {
                Log.e("Failed to download file", "error");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String string) {
                handleQuery(string);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String string) {
                handleQuery(string);
                return true;
            }
        });
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));


        return true;
    }


    private void handleQuery(String string) {
        searchResult.clear();
        Log.e("FILTER", string);
        String query = removerAcentos(string);

        if (!string.equals("")) {
            for (Music item : reset) {
                String label = removerAcentos(item.getTitulo().toLowerCase());
                String label_2 = removerAcentos(item.getCantor().toLowerCase());
                if (label.contains(query.toLowerCase()) || label_2.contains(query.toLowerCase())) {
                    if (!searchResult.contains(item)) {
                        searchResult.add(item);
                    }
                }
            }


            Log.e("FILTER", "same...");
            for (Music item :reset) {
                Log.e("MUSIC ", item.getTitulo());
            }

            Log.e("FILTER", "UPDATING...");
            for (Music item : searchResult) {
                Log.e("MUSIC ", item.getTitulo());
            }



            Log.e("FILTER", "pos " + fragment_pos);
            Changeable recyclerTempAdapter = ((Searchable) adapter.getItem(fragment_pos)).getRecyclerAdapter();
            Log.e("FILTER", "null? " + (recyclerTempAdapter == null));

            List<Music> onlyRemove = new ArrayList<Music>(recyclerTempAdapter.getmDataset());
            List<Music> onlyAdd = new ArrayList<Music>(searchResult);

            for (Music item : onlyRemove) {
                if (onlyAdd.contains(item)) onlyAdd.remove(item);
                else
                    recyclerTempAdapter.remove(item);
            }
            for (Music item : onlyAdd) {
                recyclerTempAdapter.add(recyclerTempAdapter.getItemCount(), item);
            }

        }else{
            Changeable recyclerTempAdapter = ((Searchable) adapter.getItem(fragment_pos)).getRecyclerAdapter();
            for (Music item : reset) {
                recyclerTempAdapter.add(recyclerTempAdapter.getItemCount(), item);
            }
        }
    }

    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }


    private void setupViewPager(ViewPager viewPager) {

        adapter.addFragment(votingFragment, "VOTAÇÃO");
        adapter.addFragment(rankingFragment, "RANKING");
        adapter.addFragment(myVotesFragment, "MEUS VOTOS");
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fragment_pos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private HashMap<Integer, String> mFragmentTags = new HashMap<Integer, String>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//            Bundle bundle = new Bundle();
//            bundle.putString("LIST", data);


            switch (position) {
                case 0:
                    return votingFragment;

                case 1:
                    return rankingFragment;
                case 2:
//                        myVotesFragment.setArguments(bundle);
                    return myVotesFragment;
            }
            return null;

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

//        @Override
//        public int getItemPosition(Object object) {
//            Log.d("TAG", "getItemPosition(" + object.getClass().getSimpleName() + ")");
//            if (object instanceof VotingFragment) {
//                ((RankingFragment) object).update();
//                ((MyVotesFragment) object).update();
//            }
//            return super.getItemPosition(object);
//        }
    }

}