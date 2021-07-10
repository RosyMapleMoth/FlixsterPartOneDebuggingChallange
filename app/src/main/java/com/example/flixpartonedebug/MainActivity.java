package com.example.flixpartonedebug;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixpartonedebug.adaptors.MovieAdapator;
import com.example.flixpartonedebug.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {



    private static final String URL = "https://api.themoviedb.org/3/m ovie/now_playing?api_key=%s";

    // please note that normally we would not leave API keys inside of a file we are exposing to github
    private static final String APIKey = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String TAG = "MainActivity";

    List<Movie> movies;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //init movies
        movies = new ArrayList<>();

        // set up cli
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();


        // set up Recycler view
        final MovieAdapator adapter = new MovieAdapator(this, movies);
        recyclerView = findViewById(R.id.rvMain);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this) );


        client.get(String.format(URL,APIKey), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {

                Log.i(TAG,"Async request to " + String.format(URL,APIKey) + " Success with response " + json.toString());
                JSONObject jsonObject = json.jsonObject;

                try
                {
                    // extract all movies from JSON
                    JSONArray results = jsonObject.getJSONArray("results");
                    movies.addAll(Movie.fromJsonArray(results));

                }
                catch (JSONException e)
                {
                    // There was a problem with our JSON response
                    Log.e(TAG, "Json error", e);
                }

                Log.d(TAG, "Request successful JSON : " + json.toString());
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG,"Async request failed, URL :" + String.format(URL,APIKey));
                Log.d(TAG, "JsonResponce " +  response);

            }
        });

    }
}
