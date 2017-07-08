package id.co.blogspot.tutor93.popularmovie.moviedetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.data.model.ReviewResult;
import id.co.blogspot.tutor93.popularmovie.data.model.VideoResult;
import id.co.blogspot.tutor93.popularmovie.utility.Constant;

/**
 * Created by indraaguslesmana on 7/2/17.
 */

public class MovieDetailVideoAdapter extends RecyclerView.Adapter<MovieDetailVideoAdapter.ReviewDetailHolder> {

    private List<VideoResult> mVideosResult;

    public MovieDetailVideoAdapter() {
        mVideosResult = new ArrayList<>();
    }

    @Override
    public ReviewDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);
        return new ReviewDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewDetailHolder holder, int position) {
        if (mVideosResult.get(position).site != null) {
            Glide.with(holder.listitem.getContext())
                    .load(String.format(Constant.YOUTUBE_THUMBNAIL_URL, mVideosResult.get(position).key))
                    .crossFade()
                    .centerCrop()
                    .error(R.drawable.ic_youtube)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mVideosResult.size();
    }

    class ReviewDetailHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final View listitem;

        ReviewDetailHolder(View itemView) {
            super(itemView);
            listitem = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.video_thumb);
        }
    }

    public void removeAll() {
        mVideosResult.clear();
        notifyDataSetChanged();
    }

    public void addItems(List<VideoResult> videoReuslt) {
        mVideosResult.addAll(videoReuslt);
        notifyItemRangeInserted(getItemCount(), mVideosResult.size() - 1);
    }
}
