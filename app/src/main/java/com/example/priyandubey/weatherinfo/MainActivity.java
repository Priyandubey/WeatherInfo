package com.example.priyandubey.weatherinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String weatherApi = "http://api.openweathermap.org/data/2.5/forecast?q=";
    static String  cityName = "badlapur";
    String appId = "&appid=c18b0940c3827b3d7215ca0ca46bb525&";
    String units = "units=metric";
    EditText editText;

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;

    static final public String WEATHER_CLOUDS = "Clouds";
    static final public String WEATHER_RAIN = "Rain";
    static final public String WEATHER_WIND = "Wind";
    static final public String WEATHER_SNOW = "Snow";
    static final public String WEATHER_CLEAR = "Clear";
    final static String monthNumber[] = new String[]{"January","February","Marh","April","May","June","July","August","September","October","November","December"};
    final String dayNumber[] = new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    TextView today;
    TextView todayTemp;
    TextView todayMinTemp;
    ImageView todayImage;
    TextView locationMain;
    TextView todayWeather;
    ArrayList<DailyWeatherReport> info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        today = findViewById(R.id.today);
        todayImage = findViewById(R.id.todayImage);
        todayTemp = findViewById(R.id.todayTemp);
        todayMinTemp = findViewById(R.id.todayMinTemp);
        todayWeather = findViewById(R.id.todayWeather);
        locationMain =findViewById(R.id.locationMain);
        info = new ArrayList<>();
        //        http://api.openweathermap.org/data/2.5/forecast?q=badlapur&appid=c18b0940c3827b3d7215ca0ca46bb525&units=metric
        //TODO today date

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String url = weatherApi + cityName + appId + units;

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


                    ArrayList<DailyWeatherReport> temp = new ArrayList<>();

                    @Override
                    public void onResponse(JSONObject response) {

//                        Log.i("Response- ",response.toString() );

                        try {

                            JSONObject city = response.getJSONObject("city");
                            String name = city.getString("name");
                            String country = city.getString("country");

                            JSONArray list = response.getJSONArray("list");

                            for (int i = 0;i < 6;i++){

                                JSONObject object = list.getJSONObject(i);

                                JSONObject main = object.getJSONObject("main");
                                Double currentTemp = main.getDouble("temp");
                                Double maxTemp = main.getDouble("temp_max");
                                Double minTemp = main.getDouble("temp_min");

                                JSONArray weatherArr = object.getJSONArray("weather");
                                JSONObject weather = weatherArr.getJSONObject(0);
                                String weatherType = weather.getString("main");

                                String rawDate = object.getString("dt_txt");

                                DailyWeatherReport report = new DailyWeatherReport(name,country,currentTemp.intValue(),maxTemp.intValue(),minTemp.intValue(),weatherType,rawDate);

//                                Log.i("printing from json",report.getCountry());

                                info.add(report);
                                if(i!=0) temp.add(report);

                            }

                            Calendar calendar = Calendar.getInstance();
                            int curMonth = calendar.get(Calendar.MONTH);
                            int curDayMonth = calendar.get(Calendar.DAY_OF_MONTH);
                            Log.i("day",Integer.toString(curMonth));
                            today.setText("     Today, " + monthNumber[curMonth] + " " + curDayMonth  );

                                DailyWeatherReport dr = info.get(0);

                                todayTemp.setText(dr.getCurrentTemp() + "°");
                                todayMinTemp.setText(dr.getMinTemp() + "°");
                                todayWeather.setText(dr.getWeather());
                                locationMain.setText(dr.getCity() + ", " + dr.getCountry());
                                Log.i("date",dr.getFormattedDate());

                                switch(dr.getWeather()) {

                                    case WEATHER_CLEAR:
                                        todayImage.setImageResource(R.drawable.newsun);
                                    case WEATHER_CLOUDS:
                                        todayImage.setImageResource(R.drawable.newcloud);
                                    case WEATHER_RAIN:
                                        todayImage.setImageResource(R.drawable.newrain);
                                    case WEATHER_SNOW:
                                        todayImage.setImageResource(R.drawable.newsnowman);
                                    case WEATHER_WIND:
                                        todayImage.setImageResource(R.drawable.newwind);

                                }

//                            Log.i("size inside of array",Integer.toString(temp.size()));


                            recyclerAdapter = new RecyclerAdapter(temp,getApplicationContext());
                            recyclerView.setAdapter(recyclerAdapter);

                        }catch(Exception e){

                            e.printStackTrace();

                        }



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
//        Log.i("this is the root",info.toString());
//        Log.i("size of array",Integer.toString(info.size()));




    }

    public void searchButton(View view){

        cityName = editText.getText().toString();



    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }



}
