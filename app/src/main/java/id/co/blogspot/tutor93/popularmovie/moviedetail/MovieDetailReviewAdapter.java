package id.co.blogspot.tutor93.popularmovie.moviedetail;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.data.model.ReviewResult;

/**
 * Created by indraaguslesmana on 7/2/17.
 */

public class MovieDetailReviewAdapter extends RecyclerView.Adapter<MovieDetailReviewAdapter.ReviewDetailHolder> {

    private List<ReviewResult> mReviewResults;
    private MovieReviewListListener movieReviewListListener;

    public MovieDetailReviewAdapter() {
        mReviewResults = new ArrayList<>();
        movieReviewListListener = null;
    }

    @Override
    public ReviewDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewDetailHolder holder, int position) {
        holder.mAuthor.setText(mReviewResults.get(position).author);
        holder.mContent.setText(mReviewResults.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mReviewResults.size();
    }

    class ReviewDetailHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView mAuthor, mContent;
        private final View listitem;

        ReviewDetailHolder(View itemView) {
            super(itemView);
            listitem = itemView;
            mAuthor = (AppCompatTextView) itemView.findViewById(R.id.reviews_item_author);
            mContent = (AppCompatTextView) itemView.findViewById(R.id.reviews_item_content);
            listitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (movieReviewListListener != null) {
                        movieReviewListListener.onMovieReviewListClick(
                                mContent,
                                getAdapterPosition());
                    }
                }
            });
        }
    }

    public void removeAll() {
        mReviewResults.clear();
        notifyDataSetChanged();
    }

    public void addItems(List<ReviewResult> reviewResults) {
        mReviewResults.addAll(reviewResults);
        notifyItemRangeInserted(getItemCount(), mReviewResults.size() - 1);
    }

    /**
     * Handling user click image
     */
    public interface MovieReviewListListener {
        void onMovieReviewListClick(TextView mMovieContent, int adapterPosition);
    }

    public void setMovieReviewListListener(MovieReviewListListener movieListListener) {
        movieReviewListListener = movieListListener;
    }
}
