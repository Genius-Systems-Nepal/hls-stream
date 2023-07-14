package com.multicast.udpstream.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;


import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;


import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.UdpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.multicast.udpstream.R;
import com.multicast.udpstream.activity.MainActivity;
import com.multicast.udpstream.helper.ChannelsDAO;
import com.multicast.udpstream.model.Channel;

import java.util.ArrayList;
import java.util.List;


public class Player extends AppCompatActivity {

    private PlayerView playerView;
    private int index;
    private String  ip;
    private SimpleExoPlayer player;
    private Channel channel;
    private ChannelsDAO channelsDAO;
    private List<Channel> channelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        playerView = findViewById(R.id.playerView);
        channelsDAO = new ChannelsDAO(getApplicationContext());
        channelList = channelsDAO.listar();
        ip = getIntent().getStringExtra("ip");

    }

    private void initializePlayer(){

        DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);

        trackSelector.setParameters(
                trackSelector
                        .buildUponParameters()
                        .setRendererDisabled(C.TRACK_TYPE_VIDEO, true)
                        .setRendererDisabled(C.TRACK_TYPE_AUDIO, true)
                        .setMaxVideoSizeSd()
                        .setPreferredAudioLanguage("en-und2"));


        DefaultRenderersFactory defaultRenderersFactory = new DefaultRenderersFactory(this);
        defaultRenderersFactory.setEnableDecoderFallback(true);
        defaultRenderersFactory.setEnableAudioFloatOutput(true);
        defaultRenderersFactory.setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, "exoplayer-codelab");

        MediaSource mediaSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
                MediaItem.fromUri(Uri.parse("http://180.188.254.253:8081/iptvdvr/StarBharatHD/playlist.m3u8")
                ));


        player = new SimpleExoPlayer.Builder(this, defaultRenderersFactory).setTrackSelector(trackSelector).build();

        playerView.setPlayer(player);

        player.prepare(mediaSource);
        player.seekTo(index, C.TIME_UNSET);
        player.setPlayWhenReady(true);

        hideSystemUi();
    }

    private void releasePlayer(){

        if(player != null){
            player.release();
            player = null;
        }

    }

    //To Play in fullscresn
    @SuppressLint("InlinedApi")
    private void hideSystemUi(){
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    //=========================override methods==================================

    //Override for key press events
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                //Change to next video
                Toast.makeText(getApplicationContext(), "Next", Toast.LENGTH_LONG).show();
                player.next();
                return true;
            case  KeyEvent.KEYCODE_DPAD_LEFT:
                //Change to previous video
                Toast.makeText(getApplicationContext(), "Previous", Toast.LENGTH_LONG).show();
                player.previous();
                return true;

            case  KeyEvent.KEYCODE_MEDIA_PLAY:
                //Change to previous video
                Toast.makeText(getApplicationContext(), "Play", Toast.LENGTH_LONG).show();
                player.setPlayWhenReady(true);
                return true;

            case  KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                //Change to previous video
                Toast.makeText(getApplicationContext(), "Previous ", Toast.LENGTH_LONG).show();
                player.previous();
                return true;

            case  KeyEvent.KEYCODE_MEDIA_NEXT:
                //Change to previous video
                Toast.makeText(getApplicationContext(), "Next ", Toast.LENGTH_LONG).show();
                player.next();
                return true;
            case KeyEvent.KEYCODE_BACK:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Util.SDK_INT >= 24){
            initializePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if ((Util.SDK_INT < 24 || player == null)){
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    //Release resources onPause
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }
}
