package id.co.blogspot.tutor93.popularmovie.utility.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResult;
import id.co.blogspot.tutor93.popularmovie.utility.Constant;
import id.co.blogspot.tutor93.popularmovie.utility.Helper;

/**
 * Created by indraaguslesmana on 6/17/17.
 */

public class MovieDetailFrameWrapper extends LinearLayout{

    private static final String TAG = "MovieDetailFrameWrapper";

    @BindView(R.id.moviedetail_tvReleasedate) AppCompatTextView mReleaseDate;
    @BindView(R.id.moviedetail_voteaverage) AppCompatTextView mVoteAverage;
    @BindView(R.id.moviedetail_description) AppCompatTextView mDescription;
    @BindView(R.id.moviedetail_imagemovie) AppCompatImageView mImagePoster;
    @BindView(R.id.moviedetail_rateimage) AppCompatImageView mImageRate;
    @BindView(R.id.moviedetail_title) AppCompatTextView mMovieTitle;

    public MovieDetailFrameWrapper(Context context, MovieResult movieResult) {
        super(context);
        init(context);

        if (!TextUtils.isEmpty(movieResult.title)){
            mMovieTitle.setText(movieResult.title);
        }

        if (!TextUtils.isEmpty(movieResult.releaseDate)){
            mReleaseDate.setText(parseDate(movieResult.releaseDate));
        }

        if (movieResult.voteAverage != null){
            mVoteAverage.setText(String.valueOf(movieResult.voteAverage));
            mImageRate.setImageResource(setImageRate(movieResult.voteAverage));
        }

        if (!TextUtils.isEmpty(movieResult.overview)){
            mDescription.setText(movieResult.overview);
        }

        if (!TextUtils.isEmpty(movieResult.posterPath)){
            Glide.with(context)
                    .load(Helper.getComplateImageUrl(movieResult.posterPath, Constant.POSTERSIZE_w342))
                    .error(R.drawable.ic_error_list)
                    .into(mImagePoster);
        }
    }

    private void init(Context context) {
        inflate(context, R.layout.content_moviedetail, this);
        ButterKnife.bind(this);
    }

    private String parseDate(String date) {
        SimpleDateFormat sourceDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date sourceDate = null;

        try {
            sourceDate = sourceDateFormat.parse(date);
        } catch (ParseException e) {
            Log.i(TAG, "parseDate: parse failed");
        }

        SimpleDateFormat finalDateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        return finalDateFormat.format(sourceDate);
    }

    private int setImageRate(double rate){
        int img = 0;
        if (rate <= 4.0){
            img = R.drawable.ic_rating_one;
        }else if (rate >= 4.0 && rate <= 5.0){
            img = R.drawable.ic_rating_two;
        }else if (rate >= 5.0 && rate <= 7.5){
            img = R.drawable.ic_rating_three;
        }else if (rate >= 7.5 && rate <= 8.5){
            img = R.drawable.ic_rating_four;
        }else if (rate >= 8.5 && rate <= 10.0){
            img = R.drawable.ic_rating_five;
        }else {
            img = R.drawable.ic_rating_one;
        }
        return img;
    }
}
