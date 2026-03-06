package com.example.stormcast.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stormcast.BuildConfig;
import com.example.stormcast.R;
import com.example.stormcast.network.ApiWeather;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_forecast extends Fragment {

    private static final String TAG = "Fragment_forecast";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String DEFAULT_CITY = "London";
    private static final String UNIT_METRIC = "metric";

    private TextView titleText;
    private TextView subtitleText;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ForecastAdapter adapter;
    private ApiWeather api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        titleText = view.findViewById(R.id.forecast_title);
        subtitleText = view.findViewById(R.id.forecast_subtitle);
        recyclerView = view.findViewById(R.id.forecast_recycler);
        progressBar = view.findViewById(R.id.forecast_progress);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ForecastAdapter(new ArrayList<>(), 0);
        recyclerView.setAdapter(adapter);

        initRetrofitService();
        fetchForecast(DEFAULT_CITY);
    }

    private void initRetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(ApiWeather.class);
    }

    private void fetchForecast(String city) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        Call<ForecastResponse> call = api.getForecast(city, BuildConfig.OPENWEATHER_API_KEY, UNIT_METRIC);
        
        call.enqueue(new Callback<ForecastResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                progressBar.setVisibility(View.GONE);
                
                if (response.isSuccessful() && response.body() != null) {
                    ForecastResponse forecastData = response.body();
                    subtitleText.setText(forecastData.getCity().getName() + ", " + forecastData.getCity().getCountry());
                    

                    List<ForecastResponse.ForecastItem> dailyList = filterDailyForecasts(forecastData.getList());
                    
                    adapter.updateData(dailyList, forecastData.getCity().getTimezone());
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    Log.e(TAG, "Forecast fetch failed: " + response.code());
                    subtitleText.setText("Failed to load forecast");
                }
            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "Network failure: ", t);
                Toast.makeText(getContext(), "Check Connection", Toast.LENGTH_LONG).show();
                subtitleText.setText("Network error");
            }
        });
    }

    private List<ForecastResponse.ForecastItem> filterDailyForecasts(List<ForecastResponse.ForecastItem> fullList) {
        List<ForecastResponse.ForecastItem> dailyList = new ArrayList<>();
        
        for (int i = 0; i < fullList.size(); i += 8) {
            dailyList.add(fullList.get(i));
            if (dailyList.size() == 5) break; 
        }

        return dailyList;
    }
}
