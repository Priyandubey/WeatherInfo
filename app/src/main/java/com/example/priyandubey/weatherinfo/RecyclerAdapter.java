package com.example.priyandubey.weatherinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.priyandubey.weatherinfo.MainActivity.WEATHER_CLEAR;
import static com.example.priyandubey.weatherinfo.MainActivity.WEATHER_CLOUDS;
import static com.example.priyandubey.weatherinfo.MainActivity.WEATHER_RAIN;
import static com.example.priyandubey.weatherinfo.MainActivity.WEATHER_SNOW;
import static com.example.priyandubey.weatherinfo.MainActivity.WEATHER_WIND;
import static com.example.priyandubey.weatherinfo.MainActivity.dayNumber;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<DailyWeatherReport> reportList;
    private Context mContext;

    public RecyclerAdapter(ArrayList<DailyWeatherReport> reportList, Context mContext) {
        this.reportList = reportList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        try {

            DailyWeatherReport r = reportList.get(position);
           // Log.i("array",r.getWeather());
            holder.mnTemp.setText(String.valueOf(r.minTemp) + "°");
            holder.day.setText(dayNumber[(position + dayOfWeek - 1)%7]);
            if(position == 0){
                holder.day.setText("Tomorrow");
            }
            switch (r.weather) {

                case WEATHER_CLEAR:
                    holder.image.setImageResource(R.drawable.newsun);
                    break;
                case WEATHER_CLOUDS:
                    holder.image.setImageResource(R.drawable.newcloud);
                    break;
                case WEATHER_RAIN:
                    holder.image.setImageResource(R.drawable.newrain);
                    break;
                case WEATHER_SNOW:
                    holder.image.setImageResource(R.drawable.newsnowman);
                    break;
                case WEATHER_WIND:
                    holder.image.setImageResource(R.drawable.newwind);
                    break;

            }
//            Log.i("currtemp",String.valueOf(r.currentTemp));
            holder.curTemp.setText(String.valueOf(r.currentTemp) + "°");
            holder.climate.setText(String.valueOf(r.weather));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public TextView day;
        public TextView climate;
        public TextView curTemp;
        public TextView mnTemp;

        public MyViewHolder(View itemView) {
            super(itemView);

            image  = itemView.findViewById(R.id.recyclerImage);
            day = itemView.findViewById(R.id.recyclerDay);
            climate = itemView.findViewById(R.id.recyclerReport);
            curTemp = itemView.findViewById(R.id.recyclerTemp);
            mnTemp = itemView.findViewById(R.id.recyclerMinTemp);

        }
    }

}
