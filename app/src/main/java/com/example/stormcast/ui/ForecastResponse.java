package com.example.stormcast.ui;

import java.util.List;

public class ForecastResponse {
    private String cod;
    private int message;
    private int cnt;
    private List<ForecastItem> list;
    private City city;

    public String getCod() { return cod; }
    public int getMessage() { return message; }
    public int getCnt() { return cnt; }
    public List<ForecastItem> getList() { return list; }
    public City getCity() { return city; }

    public static class ForecastItem {
        private long dt;
        private Main main;
        private List<Weather> weather;
        private Clouds clouds;
        private Wind wind;
        private int visibility;
        private double pop;
        private Sys sys;
        private String dt_txt;

        public long getDt() { return dt; }
        public Main getMain() { return main; }
        public List<Weather> getWeather() { return weather; }
        public Clouds getClouds() { return clouds; }
        public Wind getWind() { return wind; }
        public int getVisibility() { return visibility; }
        public double getPop() { return pop; }
        public Sys getSys() { return sys; }
        public String getDt_txt() { return dt_txt; }
        
        public static class Sys {
            private String pod;
            public String getPod() { return pod; }
        }
    }

    public static class City {
        private int id;
        private String name;
        private Coord coord;
        private String country;
        private int population;
        private int timezone;
        private long sunrise;
        private long sunset;

        public int getId() { return id; }
        public String getName() { return name; }
        public Coord getCoord() { return coord; }
        public String getCountry() { return country; }
        public int getPopulation() { return population; }
        public int getTimezone() { return timezone; }
        public long getSunrise() { return sunrise; }
        public long getSunset() { return sunset; }
    }
}
