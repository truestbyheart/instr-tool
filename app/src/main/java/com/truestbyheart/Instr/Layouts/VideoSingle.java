package com.truestbyheart.Instr.Layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.truestbyheart.Instr.API.models.PostModel;
import com.truestbyheart.Instr.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;


public class VideoSingle extends AppCompatActivity {

    PlayerView playerView;
    TextView postText;
    Button downloadBtn, shareBtn;


    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    PostModel postModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exoplayer);

        playerView = findViewById(R.id.video);
        postText = findViewById(R.id.post_text);
        downloadBtn = findViewById(R.id.download_video);
        shareBtn = findViewById(R.id.repost_video);


        Intent intent = getIntent();
        postModel = intent.getParcelableExtra("post");

        postText.setText(postModel.getPostText());


        downloadBtn.setOnClickListener(v -> {
           download();
        });

    }

    private  void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(player);
        Uri uri = Uri.parse(postModel.getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }


    private void download() {
        Downback DB = new Downback();
        DB.execute();
    }


    private class Downback extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            final String vidUrl = postModel.getVideoURL();
            downloadFile(vidUrl);
            return null;
        }

        private void downloadFile(String vidUrl) {
            String name = UUID.randomUUID() + ".mp4";

            try{
                String rootDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + "Instr/Video";
                File rootFile = new File(rootDir);

                if(!rootFile.exists()){
                    rootFile.mkdir();
                }

                URL url = new URL(vidUrl);

                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();

                FileOutputStream fileOutputStream = new FileOutputStream(new File(rootFile, name));

                InputStream inputStream = c.getInputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while((len = inputStream.read(buffer)) > 0){
                    Log.d("buffer", "downloadFile: " + len);
                    fileOutputStream.write(buffer, 0, len);
                }

                fileOutputStream.close();
            } catch (IOException e) {
                Log.d("download", "downloadFile: " + e);
            }
        }
    }


}
