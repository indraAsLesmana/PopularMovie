package id.co.blogspot.tutor93.popularmovie.moviehome;

import android.support.annotation.IntDef;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.data.model.MoviePopularResults;
import id.co.blogspot.tutor93.popularmovie.utility.Helper;

/**
 * Created by indraaguslesmana on 6/16/17.
 */

public class MovieHomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<MoviePopularResults> mMoviePopularsList;
    private MovieListListener mMovieHomeListListener;

    /**
     * ViewTypes serve as a mapping point to which layout should be inflated
     */
    public static final int VIEW_TYPE_GALLERY = 0;
    public static final int VIEW_TYPE_LOADING = 1;

    @IntDef({VIEW_TYPE_LOADING, VIEW_TYPE_GALLERY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }

    @ViewType
    private int mViewType;

    public MovieHomeListAdapter() {
        mMoviePopularsList = new ArrayList<>();
        mViewType = VIEW_TYPE_GALLERY;
        mMovieHomeListListener = null;
    }

    public int getViewType() {
        return mViewType;
    }

    public void setViewType(@ViewType int viewType) {
        mViewType = viewType;
    }

    @Override
    public int getItemViewType(int position) {
        return mMoviePopularsList.get(position) == null ? VIEW_TYPE_LOADING : mViewType;
    }

    @Override
    public int getItemCount() {
        return mMoviePopularsList.size();
    }

    @Override
    public long getItemId(int position) {
        return mMoviePopularsList.size() >= position ? mMoviePopularsList.get(position).id : -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return onIndicationViewHolder(parent);
        }
        return onGenericItemViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_LOADING) {
            return;
        }
        onBindGenericItemViewHolder((CharacterViewHolder) holder, position);
    }

    private void onBindGenericItemViewHolder(final CharacterViewHolder holder, int position) {
        String imageUrl = mMoviePopularsList.get(position).backdropPath;

        if (!TextUtils.isEmpty(imageUrl) && !imageUrl.equals("")) {
            Glide.with(holder.listItem.getContext())
                    .load(Helper.getComplateImageUrl(imageUrl))
                    .centerCrop()
                    .crossFade()
                    .into(holder.imageItem);
        }
    }

    private RecyclerView.ViewHolder onIndicationViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar, parent, false);
        return new ProgressBarViewHolder(view);
    }

    private RecyclerView.ViewHolder onGenericItemViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case VIEW_TYPE_GALLERY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_list, parent, false);
                break;
            // it can add if new view devine
        }
        return new CharacterViewHolder(view);
    }

    /**
     * ViewHolders
     */
    public class ProgressBarViewHolder extends RecyclerView.ViewHolder {

        public final ProgressBar progressBar;

        public ProgressBarViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        }
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder {

        public final ImageView imageItem;
        public final View listItem;

        public CharacterViewHolder(View view) {
            super(view);
            imageItem = (AppCompatImageView) view.findViewById(R.id.home_itemimage);
            listItem = view;
            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }


    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void removeAll() {
        mMoviePopularsList.clear();
        notifyDataSetChanged();
    }

    public void addItems(List<MoviePopularResults> itemsList) {
        mMoviePopularsList.addAll(itemsList);
        notifyItemRangeInserted(getItemCount(), mMoviePopularsList.size() - 1);
    }

    /**
     * Handling user click image
     */
    public interface MovieListListener {
        void onMovieListClick(MoviePopularResults moviePopular, int adapterPosition);
    }

    public void setMovieListListener(MovieListListener movieListListener) {
        mMovieHomeListListener = movieListListener;
    }
}
