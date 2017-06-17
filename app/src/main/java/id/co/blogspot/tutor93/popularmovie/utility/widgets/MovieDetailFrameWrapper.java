package id.co.blogspot.tutor93.popularmovie.utility.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResults;
import id.co.blogspot.tutor93.popularmovie.utility.Constant;
import id.co.blogspot.tutor93.popularmovie.utility.Helper;

/**
 * Created by indraaguslesmana on 6/17/17.
 */

public class MovieDetailFrameWrapper extends LinearLayout{

    private TextView mReleaseDate;
    private TextView mVoteAverage;
    private TextView mDescription;
    private ImageView mImagePoster;

    public MovieDetailFrameWrapper(Context context, MovieResults movieResults) {
        super(context);
        init(context);

        if (!TextUtils.isEmpty(movieResults.releaseDate)){
            mReleaseDate.setText(movieResults.releaseDate);
        }
        if (movieResults.voteAverage != null){
            mVoteAverage.setText(String.valueOf(movieResults.voteAverage));
        }
        if (!TextUtils.isEmpty(movieResults.overview)){
            mDescription.setText(movieResults.overview);
        }

        if (!TextUtils.isEmpty(movieResults.posterPath)){
            Glide.with(context)
                    .load(Helper.getComplateImageUrl(movieResults.posterPath, Constant.POSTERSIZE_w342))
                    .into(mImagePoster);
        }
    }

    private void init(Context context) {
        inflate(context, R.layout.content_moviedetail, this);

        mReleaseDate = (AppCompatTextView) findViewById(R.id.moviedetail_tvReleasedate);
        mVoteAverage = (AppCompatTextView) findViewById(R.id.moviedetail_voteaverage);
        mDescription = (AppCompatTextView) findViewById(R.id.moviedetail_description);
        mImagePoster = (AppCompatImageView) findViewById(R.id.moviedetail_imagemovie);
    }
}
