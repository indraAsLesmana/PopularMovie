package id.co.blogspot.tutor93.popularmovie.videoplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import id.co.blogspot.tutor93.popularmovie.BuildConfig;
import id.co.blogspot.tutor93.popularmovie.R;

/**
 * Created by indraaguslesmana on 7/9/17.
 */

public class VideoPlayerYoutube extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {

    private static final String EXTRA_MOVIE_URL = "youtubeKey";
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;

    public static Intent newStartIntent(Context context, String movieUrl) {
        Intent intent = new Intent(context, VideoPlayerYoutube.class);
        intent.putExtra(EXTRA_MOVIE_URL, movieUrl);
        return intent;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_videoplayeryoutube);

        initView();
    }

    private void initView() {
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        initYoutubePlayer();
    }

    private void initYoutubePlayer() {
        youTubeView.initialize(BuildConfig.YOUTUBE_DATAKEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        youTubePlayer.loadVideo(getIntent().getStringExtra(VideoPlayerYoutube.EXTRA_MOVIE_URL));
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            getYouTubePlayerProvider().initialize(BuildConfig.YOUTUBE_DATAKEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }
}
