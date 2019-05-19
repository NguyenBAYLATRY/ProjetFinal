package fr.esilv.projetfinal;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;


public class TVshowAdapter extends RecyclerView.Adapter<TVshowAdapter.TVshowViewHolder> {
    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";

    private List<Genre> allGenres;
    private List<TVshow> shows;

    public TVshowAdapter(List<TVshow> movies, List<Genre> allGenres) {
        this.shows = movies;
        this.allGenres = allGenres;
    }

    @Override
    public TVshowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new TVshowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TVshowViewHolder holder, int position) {
        holder.bind(shows.get(position));
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }
    public void appendMovies(List<TVshow> showsToAppend) {
        shows.addAll(showsToAppend);
        notifyDataSetChanged();
    }

    class TVshowViewHolder extends RecyclerView.ViewHolder {
        TextView releaseDate;
        TextView title;
        TextView rating;
        TextView genres;
        ImageView poster;

        public TVshowViewHolder(View itemView) {
            super(itemView);
            releaseDate = itemView.findViewById(R.id.item_movie_release_date);
            title = itemView.findViewById(R.id.item_movie_title);
            rating = itemView.findViewById(R.id.item_movie_rating);
            genres = itemView.findViewById(R.id.item_movie_genre);
            poster = itemView.findViewById(R.id.item_movie_poster);
        }

        public void bind(TVshow shows) {
            releaseDate.setText(shows.getReleaseDate().split("-")[0]);
            title.setText(shows.getTitle());
            rating.setText(String.valueOf(shows.getRating()));
            genres.setText(getGenres(shows.getGenreIds()));
            Glide.with(itemView)
                    .load(IMAGE_BASE_URL + shows.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(poster);
        }


        private String getGenres(List<Integer> genreIds) {
            List<String> showGenres = new ArrayList<>();
            for (Integer genreId : genreIds) {
                for (Genre genre : allGenres) {
                    if (genre.getId() == genreId) {
                        showGenres.add(genre.getName());
                        break;
                    }
                }
            }
            return TextUtils.join(", ", showGenres);
        }
    }
}
