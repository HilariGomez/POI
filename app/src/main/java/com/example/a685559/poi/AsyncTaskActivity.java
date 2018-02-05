package com.example.a685559.poi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;

public class AsyncTaskActivity extends Activity {

    public ArrayList<InterestPoint> POIlist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new LongOperation().execute("");
    }

    public Call<ListResponse> getResponseList() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://t21services.herokuapp.com/")
                .build();

        ServiceList serviceList = retrofit.create(ServiceList.class);

        Call<ListResponse> response = serviceList.getPointsOfInterest();
        return response;
    }

    public String getAndReadUrl() {
        String result = "";
        URL url = null;
        try {
            url = new URL("http://t21services.herokuapp.com/points");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream in = null;
        try {
            in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        result = readStream(in);

        return result;
    }

    public String readStream(InputStream in) {
        java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return getAndReadUrl();
        }

        @Override
        protected void onPostExecute(String result) {
            //Call<ListResponse> li = getResponseList();
            try {
                JSONObject json = new JSONObject(result);
                org.json.JSONArray jsonArray = (org.json.JSONArray) json.get("list");

                int l = jsonArray.length();

                POIlist = new ArrayList<InterestPoint>();

                for (int i = 0; i < jsonArray.length(); ++i) {
                    String id = jsonArray.getJSONObject(i).getString("id");
                    String title = jsonArray.getJSONObject(i).getString("title");
                    String geo = jsonArray.getJSONObject(i).getString("geocoordinates");

                    POIlist.add(new InterestPoint(id, title, geo));
                }

                Intent i = new Intent(getApplicationContext(), POIListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("POILIST", POIlist);
                startActivity(i);
                finish();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}