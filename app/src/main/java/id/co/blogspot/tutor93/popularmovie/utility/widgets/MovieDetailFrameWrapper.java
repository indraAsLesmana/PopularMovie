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

    public MovieDetailFrameWrapper(Context context, MovieResult movieResult) {
        super(context);
        init(context);

        if (!TextUtils.isEmpty(movieResult.releaseDate)){
            mReleaseDate.setText(parseDate(movieResult.releaseDate));
        }

        if (movieResult.voteAverage != null){
            mVoteAverage.setText(String.valueOf(movieResult.voteAverage));
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
}
