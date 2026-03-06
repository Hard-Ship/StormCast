package com.example.stormcast.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.stormcast.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<ForecastResponse.ForecastItem> forecastList;
    private int timezoneOffset;

    public ForecastAdapter(List<ForecastResponse.ForecastItem> forecastList, int timezoneOffset) {
        this.forecastList = forecastList;
        this.timezoneOffset = timezoneOffset;
    }

    public void updateData(List<ForecastResponse.ForecastItem> newForecastList, int newTimezoneOffset) {
        this.forecastList = newForecastList;
        this.timezoneOffset = newTimezoneOffset;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        ForecastResponse.ForecastItem item = forecastList.get(position);

        long localMillis = (item.getDt() + timezoneOffset) * 1000L;
        
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        dayFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = new Date(localMillis);
        
        holder.dayText.setText(dayFormat.format(date));
        holder.timeText.setText(timeFormat.format(date));

        if (item.getMain() != null) {
            holder.tempText.setText(String.format(Locale.getDefault(), "%.0f°C", item.getMain().getTemp()));
        }

        if (item.getWeather() != null && !item.getWeather().isEmpty()) {
            String condition = item.getWeather().get(0).getMain();
            holder.conditionText.setText(condition);
            setAnimation(holder.lottieAnim, condition);
        }
    }

    @Override
    public int getItemCount() {
        return forecastList == null ? 0 : forecastList.size();
    }

    private void setAnimation(LottieAnimationView animView, String condition) {
        if (condition == null) return;
        switch (condition.toLowerCase()) {
            case "clear":
                animView.setAnimation(R.raw.sunny);
                break;
            case "snow":
                animView.setAnimation(R.raw.snow);
                break;
            case "rain":
                animView.setAnimation(R.raw.rain);
                break;
            case "clouds":
            case "cloud":
            case "haze":
            case "mist":
            case "foggy":
                animView.setAnimation(R.raw.cloud);
                break;
            default:
                animView.setAnimation(R.raw.sunny);
                break;
        }
        animView.playAnimation();
    }

    static class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView dayText, timeText, tempText, conditionText;
        LottieAnimationView lottieAnim;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.item_day);
            timeText = itemView.findViewById(R.id.item_time);
            tempText = itemView.findViewById(R.id.item_temp);
            conditionText = itemView.findViewById(R.id.item_condition);
            lottieAnim = itemView.findViewById(R.id.item_lottie);
        }
    }
}
