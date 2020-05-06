package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.utilities.APIEndPoints;
import com.squareup.picasso.Picasso;

public class MoviesDetailActivity extends AppCompatActivity {

    ImageView moviePoster;
    TextView originalName, overview, rating, releaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        moviePoster = findViewById(R.id.iv_poster);
        originalName = findViewById(R.id.tv_original_name);
        overview = findViewById(R.id.tv_overview);
        rating = findViewById(R.id.tv_rating);
        releaseDate = findViewById(R.id.tv_release_date);

        if(getIntent().hasExtra("id")){
            setTitle(getIntent().getStringExtra("title"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            Picasso.with(MoviesDetailActivity.this)
                    .load(APIEndPoints.BASE_IMAGE_URL_780 +getIntent().getStringExtra("poster"))
                    .into(moviePoster);
            originalName.setText(getIntent().getStringExtra("original_title"));
            overview.setText(getIntent().getStringExtra("over_view"));
            rating.setText(getIntent().getStringExtra("vote_average"));
            releaseDate.setText(getIntent().getStringExtra("release_date"));
        }

    }
}
