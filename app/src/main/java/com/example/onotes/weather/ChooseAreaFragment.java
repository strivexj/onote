package com.example.onotes.weather;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import com.example.onotes.App;
import com.example.onotes.R;
import com.example.onotes.bean.City;
import com.example.onotes.datebase.CityDbHelper;
import com.example.onotes.view.SideBar;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by cwj on 2017/3/9 13:52
 */
public class ChooseAreaFragment extends Fragment {

    private EditText searchText;
    private SearchView mSearchView;
    private ListView listView;


    public static String[] INDEX_STRING = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    public static int[]indexposition=new int[26];

    private SideBar sideBar;

    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();


    /**
     * city list
     */
    private List<City> cityList = new ArrayList<>();


/**
 * id : CN101010100
 * cityEn : beijing
 * cityZh : 北京
 * provinceEn : beijing
 * provinceZh : 北京
 * leaderEn : beijing
 * leaderZh : 北京
 * lat : 39.904989
 * lon : 116.405285
 */

    public  void search() {
        dataList.clear();
        CityDbHelper cityDbHelper = new CityDbHelper(getActivity());
        SQLiteDatabase db = cityDbHelper.getWritableDatabase();
        Log.d("db", "search");
        Cursor cursor = db.query("City", null, null, null, null, null, "CityEn");
        int j = 0;
        for(int i=0;i<26;i++){
            indexposition[i]=-1;
        }
        //dataList.add("A");
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                String setId = cursor.getString(cursor.getColumnIndex("cityid"));
                String CityEn = cursor.getString(cursor.getColumnIndex("cityEn"));
                String CityZh = cursor.getString(cursor.getColumnIndex("cityZh"));
                String provinceEn = cursor.getString(cursor.getColumnIndex("provinceEn"));
                String provinceZh = cursor.getString(cursor.getColumnIndex("provinceZh"));
                String leaderEn = cursor.getString(cursor.getColumnIndex("leaderEn"));
                String leaderZh = cursor.getString(cursor.getColumnIndex("leaderZh"));
                String lat = cursor.getString(cursor.getColumnIndex("lat"));
                String lon = cursor.getString(cursor.getColumnIndex("lon"));
                city.setId(setId);
                city.setCityEn(CityEn);
                city.setCityZh(CityZh);
                city.setProvinceEn(provinceEn);
                city.setProvinceZh(provinceZh);
                city.setLeaderEn(leaderEn);
                city.setLeaderZh(leaderZh);
                city.setLat(lat);
                city.setLon(lon);
                city.setSortLetters(CityEn.charAt(0)+"");
                cityList.add(city);
                Log.d("db", "find a city  " + j);

               /* for(int i=0;i<26;i++){
                    Log.d("db", "189");
                    String ind=city.getCityEn().charAt(0)+"";
                    ind=ind.toUpperCase();
                    Log.d("db", ind);
                    if(ind.equals(INDEX_STRING[i])){
                        for(int k=0;k<26;k++){
                            if(indexposition[k]!=-1){
                                dataList.add(ind);
                                Log.d("db", "159");
                                indexposition[k]=dataList.size()-1;
                            }
                        }
                    }
                }
*/
                dataList.add(cityList.get(j).getCityZh());
                adapter.notifyDataSetChanged();
                j++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }


    @Override
    public void onAttach(Context context) {
        Log.d("db", "onattach");
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        Log.d("db", "oncreateview");
        searchText = (EditText) view.findViewById(R.id.title_text);
        listView = (ListView) view.findViewById(R.id.list_view);

        //filter space
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("search", "" + cityList.size());
                for (int i = 0; i < cityList.size(); i++) {
                    if (searchText.getText().toString().equals(cityList.get(i).getCityZh())) {
                        dataList.clear();
                        dataList.add(cityList.get(i).getCityZh());
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        });
        //mSearchView=(SearchView)view.findViewById(R.id.searchView);

        adapter = new ArrayAdapter<>(getActivity().getApplication(), android.R.layout.simple_list_item_1, dataList);

        listView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("db", "onActivityCreated");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("db", "onActivityCreated");
                String weatherId = "";
                if (dataList.size() == 1) {
                    for (int i = 0; i < cityList.size(); i++) {
                        if (dataList.get(position).equals(cityList.get(i).getCityZh())) {
                            weatherId = cityList.get(i).getId();
                            break;
                        }
                    }
                } else {
                    weatherId = cityList.get(position).getId();
                }

                SharedPreferences.Editor editor = App.getContext().getSharedPreferences("weather",MODE_PRIVATE).edit();
                editor.putString("weatherid", weatherId);
                editor.apply();
                if (getActivity() instanceof WeatherMainActivity) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), WeatherActivity.class);
                    intent.putExtra("weather_id", weatherId);
                    startActivity(intent);
                    getActivity().finish();
                    Log.d("refresh", "start ");
                } else if (getActivity() instanceof WeatherActivity) {
                    WeatherActivity activity = (WeatherActivity) getActivity();
                    activity.drawerLayout.closeDrawers();
                    activity.swipeRefresh.setRefreshing(true);
                    Log.d("refresh", "instanceof ");
                    activity.requestWeather(weatherId);

                }
            }
        });


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        search();
                    }
                });

       // adapter = new WeatherAdapter(getActivity().getApplication(), cityList);
      //  listView.setAdapter(adapter);
       // adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("db", "onstart");
    }

    @Override
    public void onResume() {
        Log.d("db", "onresume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("db", "onpause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("db", "onstop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {

        Log.d("db", "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("db", "onDestroy");
        super.onDestroy();
    }


}