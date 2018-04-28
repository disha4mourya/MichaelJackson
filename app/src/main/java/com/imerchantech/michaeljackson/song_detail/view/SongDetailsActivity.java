package com.imerchantech.michaeljackson.song_detail.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

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
        intent.putExtra(Constants.TRACKPRICE, songsEntity.getTrackPrice());
        intent.putExtra(Constants.COLLECTIONNAME, songsEntity.getCollectionName());
        intent.putExtra(Constants.COLLECTIONPRICE, songsEntity.getCollectionPrice());
        intent.putExtra(Constants.COLLECTIONURL, songsEntity.getCollectionViewUrl());
        intent.putExtra(Constants.ARTISTPROFILE, songsEntity.getArtistViewUrl());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_song_details);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageLoader = new ImageLoader(getApplicationContext());

        String imageUrl = getIntent().getStringExtra(Constants.ARTISTIMAGE);
        String trackName = getIntent().getStringExtra(Constants.TRACKNAME);
        String artistName = getIntent().getStringExtra(Constants.ARTISTNAME);
        String trackPrice = getIntent().getStringExtra(Constants.TRACKPRICE);
        String collectionName = getIntent().getStringExtra(Constants.COLLECTIONNAME);
        String collectionPrice = getIntent().getStringExtra(Constants.COLLECTIONPRICE);
        final String collectionUrl = getIntent().getStringExtra(Constants.COLLECTIONURL);
        final String artictUrl = getIntent().getStringExtra(Constants.ARTISTPROFILE);

        imageLoader.DisplayImage(imageUrl, binding.ivArtistProfile);
        binding.tvTrackName.setText(trackName);
        binding.tvArtistName.setText(artistName);
        binding.tvPrice.setText(String.format("$%s", trackPrice));
        binding.tvCollectionPrice.setText(String.format("$%s", collectionPrice));
        binding.tvCollectionName.setText(collectionName);

        binding.btnViewCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(collectionUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        binding.btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(artictUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressWork();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        onBackPressWork();
        super.onBackPressed();
    }

    public void onBackPressWork() {
        finish();
    }

}
