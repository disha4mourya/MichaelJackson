package com.imerchantech.michaeljackson.song_detail.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.imerchantech.michaeljackson.R;
import com.imerchantech.michaeljackson.databinding.ActivitySongDetailsBinding;
import com.imerchantech.michaeljackson.songs_list.entity.SongsEntity;
import com.imerchantech.michaeljackson.utils.Constants;
import com.imerchantech.michaeljackson.utils.image_loading.ImageLoader;

public class SongDetailsActivity extends AppCompatActivity {

    public ImageLoader imageLoader;
    ActivitySongDetailsBinding binding;

    public static void start(Context context, SongsEntity songsEntity) {

        Intent intent = new Intent(context, SongDetailsActivity.class);
        intent.putExtra(Constants.ARTISTIMAGE, songsEntity.getArtworkUrl100());
        intent.putExtra(Constants.ARTISTNAME, songsEntity.getArtistName());
        intent.putExtra(Constants.TRACKNAME, songsEntity.getTrackName());

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_song_details);

        imageLoader = new ImageLoader(getApplicationContext());

        String imageUrl = getIntent().getStringExtra(Constants.ARTISTIMAGE);
        String trackName = getIntent().getStringExtra(Constants.TRACKNAME);
        String artistName = getIntent().getStringExtra(Constants.ARTISTNAME);

        // songsEntity = getIntent().getParcelableExtra(Constants.SONGDETAILS);
        imageLoader.DisplayImage(imageUrl, binding.ivArtistProfile);
        binding.tvTrackName.setText(trackName);
        binding.tvArtistName.setText(artistName);

    }

}
