package id.co.blogspot.tutor93.popularmovie.moviehome;

import android.database.Cursor;
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
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResult;
import id.co.blogspot.tutor93.popularmovie.utility.Constant;
import id.co.blogspot.tutor93.popularmovie.utility.Helper;

/**
 * Created by indraaguslesmana on 6/16/17.
 */

public class MovieHomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MovieHomeListAdapter";

    private final List<MovieResult> mMoviePopularsList;
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
        // TODOFIX: centerCrop option used, to make match parent image with placeholder but it issue
        //  its couse jagged image scalling when move with animation to detail activity, and couse glide load again new image.
        // FIX : after used butterknife, image jump to detail activity without reloading image again from URL

        String imageUrl = mMoviePopularsList.get(position).posterPath;
        if (!TextUtils.isEmpty(imageUrl) && !imageUrl.equals("")) {
            Glide.with(holder.listItem.getContext())
                    .load(Helper.getComplateImageUrl(imageUrl, Constant.POSTERSIZE_w342))
                    .error(R.drawable.ic_error_list)
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

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void removeAll() {
        mMoviePopularsList.clear();
        notifyDataSetChanged();
    }

    public void addItems(List<MovieResult> itemsList) {
        mMoviePopularsList.addAll(itemsList);
        notifyItemRangeInserted(getItemCount(), mMoviePopularsList.size() - 1);
    }

    /**
     * Swaps the Cursor currently held in the adapter with a new one
     * and triggers a UI refresh
     *
     * @param newCursor the new cursor that will replace the existing one
     */
    /*public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }*/

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

        public CharacterViewHolder(View itemView) {
            super(itemView);
            imageItem = (AppCompatImageView) itemView.findViewById(R.id.home_itemimage);
            listItem = itemView;
            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMovieHomeListListener != null) {
                        mMovieHomeListListener.onMovieListClick(
                                mMoviePopularsList.get(getAdapterPosition()), imageItem,
                                getAdapterPosition());
                    }
                }
            });
        }
    }

    /**
     * Handling user click image
     */
    public interface MovieListListener {
        void onMovieListClick(MovieResult movieDetail, View sharedElementView, int adapterPosition);
    }

    public void setMovieListListener(MovieListListener movieListListener) {
        mMovieHomeListListener = movieListListener;
    }
}
