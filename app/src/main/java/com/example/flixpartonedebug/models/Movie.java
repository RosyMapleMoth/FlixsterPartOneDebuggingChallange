package com.example.flixpartonedebug.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private String title;
    private String overview;
    private String posterPath;
    private static final String TAG = "Movie";
    private static final String posterPathStatic = "https://image.tmdb.org/t/p/w342%s";


    // basic constructor
    public Movie(JSONObject jsonObject) throws JSONException {
        setTitle(jsonObject.getString("Title"));
        setOverview(jsonObject.getString("overview"));
        setPosterPath(jsonObject.getString("poster_path"));
    }


    // So we can take TMDB JsonArrays and return them as List<movie> objects
    static public List<Movie> fromJsonArray(JSONArray jsonArray) throws JSONException
    {
        List<Movie> ReturnMovies = new ArrayList<>();

        // call the movie constructor for all movies in our jsonArray
        for (int i = 0; i < jsonArray.length(); i++)
        {
            ReturnMovies.add( new Movie(jsonArray.getJSONObject(i)) );
        }

        return ReturnMovies;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return String.format(posterPathStatic,posterPath);
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
