package com.example.stormcast.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.stormcast.BuildConfig;
import com.example.stormcast.R;
import com.example.stormcast.network.ApiWeather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_weather extends Fragment {

    // Constants
    private static final String TAG = "Fragment_weather";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String DEFAULT_CITY = "London";
    private static final String UNIT_METRIC = "metric";

    // UI Components - Primary
    private LottieAnimationView lottieAnimationView;
    private TextView citytxt;
    private TextView tmptxt;
    private TextView conditiontxt;
    private TextView day_date_txt; // New combined date view
    private TextView daytxt; // Legacy, kept for compatibility (hidden)
    private TextView datetxt; // Legacy, kept for compatibility (hidden)
    private TextView windspeedtxt;
    private TextView humidtxt;
    private SearchView searchbar;
    private TextView feelsliketxt;
    private TextView minmaxtxt;
    private TextView pressuretxt;
    private TextView visibilitytxt;
    private TextView sunrisetxt;
    private TextView sunsettxt;

    // Networking
    private ApiWeather api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        initViews(view);
        initRetrofitService();
        setupSearchListener();

        // Initial weather fetch
        fetchWeather(DEFAULT_CITY);

        return view;
    }

    private void initViews(View view) {
        // Primary metrics
        lottieAnimationView = view.findViewById(R.id.lottieAnimationView);
        citytxt        = view.findViewById(R.id.citytxt);
        daytxt         = view.findViewById(R.id.daytxt);
        datetxt        = view.findViewById(R.id.datetxt);
        tmptxt         = view.findViewById(R.id.tmptxt);
        conditiontxt   = view.findViewById(R.id.conditiontxt);
        day_date_txt   = view.findViewById(R.id.day_date_txt);
        searchbar      = view.findViewById(R.id.searchBar);

        // Extended metrics (via includes)
        setupDetailItem(view.findViewById(R.id.include_humidity), R.drawable.humidity, getString(R.string.humidity_label));
        setupDetailItem(view.findViewById(R.id.include_wind), R.drawable.wind, getString(R.string.wind_label));
        setupDetailItem(view.findViewById(R.id.include_pressure), R.drawable.pressure, getString(R.string.pressure_label));
        setupDetailItem(view.findViewById(R.id.include_feels_like), R.drawable.feelslike, getString(R.string.feels_like_label));
        setupDetailItem(view.findViewById(R.id.include_min_max), R.drawable.temp_minmax, "Min/Max");
        setupDetailItem(view.findViewById(R.id.include_visibility), R.drawable.visibility, getString(R.string.visibility_label));

        humidtxt       = view.findViewById(R.id.include_humidity).findViewById(R.id.detailValue);
        windspeedtxt   = view.findViewById(R.id.include_wind).findViewById(R.id.detailValue);
        pressuretxt    = view.findViewById(R.id.include_pressure).findViewById(R.id.detailValue);
        feelsliketxt   = view.findViewById(R.id.include_feels_like).findViewById(R.id.detailValue);
        minmaxtxt      = view.findViewById(R.id.include_min_max).findViewById(R.id.detailValue);
        visibilitytxt  = view.findViewById(R.id.include_visibility).findViewById(R.id.detailValue);

        sunrisetxt     = view.findViewById(R.id.sunrisetxt);
        sunsettxt      = view.findViewById(R.id.sunsettxt);
    }

    private void setupDetailItem(View root, int iconRes, String label) {
        ((android.widget.ImageView) root.findViewById(R.id.detailIcon)).setImageResource(iconRes);
        ((TextView) root.findViewById(R.id.detailLabel)).setText(label);
    }

    private void initRetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ApiWeather.class);
    }

    private void setupSearchListener() {
        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchWeather(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    public void fetchWeather(String city) {
        Call<Weathermodal> call = api.getweather(city, BuildConfig.OPENWEATHER_API_KEY, UNIT_METRIC);

        call.enqueue(new Callback<Weathermodal>() {
            @Override
            public void onResponse(Call<Weathermodal> call, Response<Weathermodal> response) {
                Weathermodal data = response.body();
                if (response.isSuccessful() && data != null) {
                    displayWeatherData(data);
                } else {
                    Log.e(TAG, "Search failed or city not found: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Weathermodal> call, Throwable t) {
                Log.e(TAG, "Network failure: ", t);
                Toast.makeText(getContext(), "Check Connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayWeatherData(Weathermodal data) {
        String mainCondition = data.getWeather().get(0).getMain();
        int timezoneOffset   = data.getTimezone();

        // Primary metrics
        citytxt.setText(data.getName());
        tmptxt.setText(getString(R.string.temp_format, data.getMain().getTemp()));
        conditiontxt.setText(mainCondition);
        windspeedtxt.setText(getString(R.string.wind_speed_format, data.getWind().getSpeed()));
        humidtxt.setText(getString(R.string.humidity_value, data.getMain().getHumidity()));

        // Date and Day
        String day = formatUnixDate(data.getDt(), timezoneOffset, "EEEE");
        String date = formatUnixDate(data.getDt(), timezoneOffset, "dd MMMM");
        day_date_txt.setText(String.format("%s, %s", day, date));

        // Extended metrics
        feelsliketxt.setText(getString(R.string.feels_like_format, data.getMain().getFeels_like()));
        minmaxtxt.setText(getString(R.string.min_max_format, 
                data.getMain().getTemp_min(), 
                data.getMain().getTemp_max()));
        pressuretxt.setText(getString(R.string.pressure_value, data.getMain().getPressure()));
        visibilitytxt.setText(getString(R.string.visibility_format, data.getVisibility() / 1000.0));
        
        sunrisetxt.setText(formatUnixTime(data.getSys().getSunrise(), timezoneOffset));
        sunsettxt.setText(formatUnixTime(data.getSys().getSunset(), timezoneOffset));

        updateWeatherAnimation(mainCondition);
        Log.d(TAG, "Weather updated: " + data.getName());
    }

    private String formatUnixTime(long utcSeconds, int timezoneOffsetSeconds) {
        long localMillis = (utcSeconds + timezoneOffsetSeconds) * 1000L;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC")); // offset is already applied manually
        return sdf.format(new Date(localMillis));
    }


    private String formatUnixDate(long utcSeconds, int timezoneOffsetSeconds, String pattern) {
        long localMillis = (utcSeconds + timezoneOffsetSeconds) * 1000L;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date(localMillis));
    }

    private void updateWeatherAnimation(String condition) {
        if (condition == null) return;

        switch (condition.toLowerCase()) {
            case "clear":
                lottieAnimationView.setAnimation(R.raw.sunny);
                break;

            case "snow":
                lottieAnimationView.setAnimation(R.raw.snow);
                break;

            case "rain":
                lottieAnimationView.setAnimation(R.raw.rain);
                break;

            case "clouds":
            case "cloud":
            case "haze":
            case "mist":
            case "foggy":
                lottieAnimationView.setAnimation(R.raw.cloud);
                break;

            default:
                lottieAnimationView.setAnimation(R.raw.sunny);
                break;
        }
        lottieAnimationView.playAnimation();
    }
}