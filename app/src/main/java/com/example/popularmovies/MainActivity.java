package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.popularmovies.adapter.MoviesAdapter;
import com.example.popularmovies.model.PopularMovies;
import com.example.popularmovies.utilities.APIEndPoints;
import com.example.popularmovies.utilities.NetworkUtilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieItemListClickListener {

    private static final String TAG = "MainActivity";

    private RecyclerView moviesRv;
    RecyclerView.LayoutManager layoutManager;
    List<PopularMovies> moviesList;
    MoviesAdapter moviesAdapter;

    TextView noDataTv;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesRv = findViewById(R.id.rv_movies);
        noDataTv = findViewById(R.id.tv_data_not_found);
        loading = findViewById(R.id.pb_loading);

        moviesList = new ArrayList<>();
        layoutManager = new GridLayoutManager(MainActivity.this, 3, RecyclerView.VERTICAL, false);
        moviesAdapter = new MoviesAdapter(MainActivity.this, moviesList, MainActivity.this);
        moviesRv.setLayoutManager(layoutManager);
        moviesRv.setAdapter(moviesAdapter);

        loadMoviesData();
    }

    private void loadMoviesData() {
        fetchPopularMovies();
    }

    @Override
    public void OnListItemClicked(int position) {
        Intent detailMovie = new Intent(MainActivity.this, MoviesDetailActivity.class);
        detailMovie.putExtra("id", moviesList.get(position).getId());
        detailMovie.putExtra("original_title", moviesList.get(position).getOriginal_title());
        detailMovie.putExtra("title", moviesList.get(position).getTitle());
        detailMovie.putExtra("poster", moviesList.get(position).getBackdrop_path());
        detailMovie.putExtra("over_view", moviesList.get(position).getOverview());
        detailMovie.putExtra("vote_average", moviesList.get(position).getVote_average());
        detailMovie.putExtra("release_date", moviesList.get(position).getRelease_date());
        startActivity(detailMovie);
    }

    private void fetchPopularMovies(){
        loading.setVisibility(View.VISIBLE);
        String url = NetworkUtilities.buildUrl(APIEndPoints.PARAM_POPULAR_MOVIE);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.setVisibility(View.GONE);
                Log.d(TAG, "onResponse: "+ response);
                try {
                    JSONObject rootObject = new JSONObject(response);

                    JSONArray resultArray = rootObject.getJSONArray("results");
                    if (resultArray.length() != 0){
                        noDataTv.setVisibility(View.GONE);
                        for (int i=0; i<resultArray.length(); i++){
                            JSONObject resultObject = resultArray.getJSONObject(i);
                            PopularMovies popularMovies = new PopularMovies();
                            popularMovies.setPopularity(resultObject.getString("popularity"));

                            popularMovies.setVote_count(resultObject.getString("vote_count"));
                            popularMovies.setVideo(resultObject.getString("video"));
                            popularMovies.setPoster_path(resultObject.getString("poster_path"));
                            popularMovies.setId(resultObject.getString("id"));
                            popularMovies.setAdult(resultObject.getString("adult"));
                            popularMovies.setBackdrop_path(resultObject.getString("backdrop_path"));
                            popularMovies.setOriginal_language(resultObject.getString("original_language"));
                            popularMovies.setOriginal_title(resultObject.getString("original_title"));

                            popularMovies.setTitle(resultObject.getString("title"));
                            popularMovies.setVote_average(resultObject.getString("vote_average"));
                            popularMovies.setOverview(resultObject.getString("overview"));
                            popularMovies.setRelease_date(resultObject.getString("release_date"));
                            moviesList.add(popularMovies);
                        }
                    }else{
                        noDataTv.setVisibility(View.VISIBLE);
                    }
                    moviesAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.setVisibility(View.GONE);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void fetchTopRatedMovies(){
        loading.setVisibility(View.VISIBLE);
        String url = NetworkUtilities.buildUrl(APIEndPoints.PARAM_TOP_RATED);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.setVisibility(View.GONE);
                Log.d(TAG, "onResponse: "+ response);
                try {
                    JSONObject rootObject = new JSONObject(response);

                    JSONArray resultArray = rootObject.getJSONArray("results");
                    if (resultArray.length() != 0){
                        noDataTv.setVisibility(View.GONE);
                        for (int i=0; i<resultArray.length(); i++){
                            JSONObject resultObject = resultArray.getJSONObject(i);
                            PopularMovies popularMovies = new PopularMovies();
                            popularMovies.setPopularity(resultObject.getString("popularity"));

                            popularMovies.setVote_count(resultObject.getString("vote_count"));
                            popularMovies.setVideo(resultObject.getString("video"));
                            popularMovies.setPoster_path(resultObject.getString("poster_path"));
                            popularMovies.setId(resultObject.getString("id"));
                            popularMovies.setAdult(resultObject.getString("adult"));
                            popularMovies.setBackdrop_path(resultObject.getString("backdrop_path"));
                            popularMovies.setOriginal_language(resultObject.getString("original_language"));
                            popularMovies.setOriginal_title(resultObject.getString("original_title"));

                            popularMovies.setTitle(resultObject.getString("title"));
                            popularMovies.setVote_average(resultObject.getString("vote_average"));
                            popularMovies.setOverview(resultObject.getString("overview"));
                            popularMovies.setRelease_date(resultObject.getString("release_date"));
                            moviesList.add(popularMovies);
                        }
                    }else{
                        noDataTv.setVisibility(View.VISIBLE);
                    }
                    moviesAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.setVisibility(View.GONE);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int pos = item.getItemId();

        switch (pos){
            case R.id.action_popular:
                moviesList.clear();
                fetchPopularMovies();
            break;
            case R.id.action_high_rated:
                moviesList.clear();
                fetchTopRatedMovies();
            break;
        }

        return true;
    }
}
