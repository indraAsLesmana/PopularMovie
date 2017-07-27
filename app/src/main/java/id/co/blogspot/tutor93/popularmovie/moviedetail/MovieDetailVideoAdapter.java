package id.co.blogspot.tutor93.popularmovie.moviedetail;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.data.model.VideoResult;
import id.co.blogspot.tutor93.popularmovie.utility.Constant;

/**
 * Created by indraaguslesmana on 7/2/17.
 */

public class MovieDetailVideoAdapter extends RecyclerView.Adapter<MovieDetailVideoAdapter.VideoHolder> {

    private List<VideoResult> mVideosResult;
    private VideoListListener mVideoListListener;

    public MovieDetailVideoAdapter() {
        mVideosResult = new ArrayList<>();
        mVideoListListener = null;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        if (mVideosResult.get(position).site != null) {
            Glide.with(holder.listItem.getContext())
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

    class VideoHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final View listItem;

        VideoHolder(View itemView) {
            super(itemView);
            imageView = (AppCompatImageView) itemView.findViewById(R.id.video_thumb);
            listItem = itemView;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mVideoListListener != null) {
                        mVideoListListener.onVideoListClick(
                                mVideosResult.get(getAdapterPosition()).key,
                                getAdapterPosition());

                    }
                }
            });
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

    public String shareClick() {
        String result = null;
        if (mVideosResult.size() > 0) {
            result = mVideosResult.get(0).key;
        }
        return result;
    }

    /**
     * Handling user click image
     */
    public interface VideoListListener {
        void onVideoListClick(String movieUrl, int adapterPosition);
    }

    public void setVideoListListener(VideoListListener movieListListener) {
        mVideoListListener = movieListListener;
    }
}
