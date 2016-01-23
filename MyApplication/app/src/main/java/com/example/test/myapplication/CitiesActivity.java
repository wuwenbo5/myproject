package com.example.test.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2015/11/25.
 */
public class CitiesActivity extends Activity{

    private Spinner provinceSpinner;
    private Spinner citySpinner;
    private Spinner areaSpinner;

    private String currentProvince;
    private String CurrentCity;
    private String currentArea;
    private JSONArray mJsonArray;
    private String[] mProvinces;
    private Map<String,String[]> mProvincesMap =new HashMap<String,String[]>();
    private Map<String,String[]> mCitiesMap =new HashMap<String,String[]>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cities_layout);
        initCitiesData();
        initViews();
    }

    private void initViews() {
        provinceSpinner = (Spinner) findViewById(R.id.province);
        citySpinner = (Spinner) findViewById(R.id.city);
        areaSpinner = (Spinner) findViewById(R.id.area);

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mProvinces);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        provinceSpinner.setAdapter(arrayAdapter);
        provinceSpinner.setScrollBarStyle(Spinner.SCROLLBARS_OUTSIDE_OVERLAY);
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                invalidateCitySpinner(currentProvince = mProvinces[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void invalidateCitySpinner(String province) {
        final String[] cities = mProvincesMap.get(province);
        ArrayAdapter cityAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,cities);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setScrollBarStyle(Spinner.SCROLLBARS_OUTSIDE_INSET);
        citySpinner.setAdapter(cityAdapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                invalidateAreaSpinner(cities[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void invalidateAreaSpinner( String city) {
        String[] areas = mCitiesMap.get(city);
        if (areas!=null&&areas.length>0){
            areaSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter areaAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,areas);
            areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            citySpinner.setScrollBarStyle(Spinner.SCROLLBARS_INSIDE_INSET);
            areaSpinner.setAdapter(areaAdapter);
        }else {
            areaSpinner.setVisibility(View.GONE);
        }
    }

    private void initCitiesData() {
        JSONObject provinceDatas = getJsonDatas();
        try {
            JSONArray jsonArray = provinceDatas.getJSONArray("citylist");
            mProvinces = new String[jsonArray.length()];
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String province = jsonObject.getString("p");
                mProvinces[i]=province;
                JSONArray cityJsonArray = null;
                try{
                    cityJsonArray = jsonObject.getJSONArray("c");
                }catch (Exception e){
                    continue;
                }

                String[] citiesArray = new String[cityJsonArray.length()];
                for (int j=0;j<citiesArray.length;j++){

                    JSONObject citiesJsonObject = cityJsonArray.getJSONObject(j);
                    String city = citiesJsonObject.getString("n");
                    citiesArray[j] = city;
                    JSONArray areaJsonArray = null;
                    try{
                        areaJsonArray = citiesJsonObject.getJSONArray("a");
                    }catch (Exception e){
                        continue;
                    }

                    String areaArray[] = new String[areaJsonArray.length()];
                    for (int k=0;k<areaJsonArray.length();k++){
                        areaArray[k] = areaJsonArray.getJSONObject(k).getString("s");
                    }
                    mCitiesMap.put(city,areaArray);
                }
                mProvincesMap.put(province,citiesArray);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("---","-------mProvincesMap--"+mProvincesMap.toString());
        Log.i("---","-------mCitiesMap--"+mCitiesMap.toString());
    }

    private JSONObject getJsonDatas() {
        JSONObject jsonObject = null;
        StringBuffer jsonString = new StringBuffer();
        try {
            InputStream ios = getAssets().open("city.json");
            int len = -1;
            byte[] bytes = new byte[1024];
            while ((len=ios.read(bytes))!=-1){

                jsonString.append(new String(bytes,0,len,"gbk"));

            }
            ios.close();
            jsonObject = new JSONObject(jsonString.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
