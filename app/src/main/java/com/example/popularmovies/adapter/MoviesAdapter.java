package com.example.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.PopularMovies;
import com.example.popularmovies.utilities.APIEndPoints;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    MovieItemListClickListener movieItemListClickListener;

    Context mContext;
    private List<PopularMovies> popularMoviesList;

    public MoviesAdapter(Context mContext, List<PopularMovies> popularMoviesList, MovieItemListClickListener itemListClickListener) {
        this.mContext = mContext;
        this.popularMoviesList = popularMoviesList;
        this.movieItemListClickListener = itemListClickListener;
    }

    public interface MovieItemListClickListener{
        void OnListItemClicked(int position);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movies_item_list, parent, false);
        return new MovieViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return popularMoviesList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView movieName;
        ImageView moviePoster;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            movieName = itemView.findViewById(R.id.tv_movie_name);
            moviePoster = itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPos = getAdapterPosition();
            movieItemListClickListener.OnListItemClicked(itemPos);
        }

        void bind(int position){
            movieName.setText(popularMoviesList.get(position).getOriginal_title());
            Picasso.with(mContext)
                    .load(APIEndPoints.BASE_IMAGE_URL_500 +popularMoviesList.get(position).getPoster_path())
                    .into(moviePoster);
        }
    }
}
