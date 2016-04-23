package com.ufcg.virtualmusicbox;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static boolean allowRefresh;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static Music[] mArr;
    private static ArrayList<Music> mVoted = new ArrayList<>();
    private static ArrayList<Music> mPlayList = new ArrayList<>();
    private String data;

    public static ArrayList<Music> getVoted() {
        return mVoted;
    }

    public static void setVoted(Music voted, boolean b) {
        if(b) {
            mVoted.add(voted);
        }else {
            mVoted.remove(voted);
        }
    }
    public static  ArrayList<Music> getRank(){
        ArrayList<Music> rank = new ArrayList<>();
        for (Music m: mPlayList){
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
            mPlayList = gson.fromJson(String.valueOf(json), new TypeToken<ArrayList<Music>>() {}.getType());

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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "VOTAÇÃO");
        adapter.addFragment(new TwoFragment(), "RANKING");
        adapter.addFragment(new ThreeFragment(), "MEUS VOTOS");
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
    }




    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private HashMap<Integer,String> mFragmentTags = new HashMap<Integer,String>();

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
                        OneFragment oneFragment = new OneFragment();
//                        oneFragment.setArguments(bundle);
                        return oneFragment;

                    case 1:
                        TwoFragment twoFragment = new TwoFragment();
//                        twoFragment.setArguments(bundle);
                        return twoFragment;
                    case 2:
                        ThreeFragment threeFragment = new ThreeFragment();
//                        threeFragment.setArguments(bundle);
                        return threeFragment;
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
//            if (object instanceof OneFragment) {
//                ((TwoFragment) object).update();
//                ((ThreeFragment) object).update();
//            }
//            return super.getItemPosition(object);
//        }


        /*


        JSON de teste
        [
    {
        "titulo" : "olat",
        "cantor" : "olac",
        "votos" : "2"
    },
    {
        "titulo" : "olat1",
        "cantor" : "olac1",
        "votos" : "22"
    },
    {
        "titulo" : "olat2",
        "cantor" : "olac2",
        "votos" : "3"
    },
    {
        "titulo" : "olat3",
        "cantor" : "olac3",
        "votos" : "42"
    },
    {
        "titulo" : "olat4",
        "cantor" : "olac4",
        "votos" : "52"
    }
]
        */




    }


}