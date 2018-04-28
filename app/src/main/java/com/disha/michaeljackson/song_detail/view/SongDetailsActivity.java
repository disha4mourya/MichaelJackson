package com.disha.michaeljackson.song_detail.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.disha.michaeljackson.R;
import com.disha.michaeljackson.databinding.ActivitySongDetailsBinding;
import com.disha.michaeljackson.utils.Constants;
import com.disha.michaeljackson.utils.DownloadImagesAsync;

public class SongDetailsActivity extends AppCompatActivity {

    ActivitySongDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_song_details);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String imageUrl = getIntent().getStringExtra(Constants.ARTISTIMAGE);
        String trackName = getIntent().getStringExtra(Constants.TRACKNAME);
        String artistName = getIntent().getStringExtra(Constants.ARTISTNAME);
        String trackPrice = getIntent().getStringExtra(Constants.TRACKPRICE);
        String collectionName = getIntent().getStringExtra(Constants.COLLECTIONNAME);
        String collectionPrice = getIntent().getStringExtra(Constants.COLLECTIONPRICE);
        final String collectionUrl = getIntent().getStringExtra(Constants.COLLECTIONURL);
        final String artictUrl = getIntent().getStringExtra(Constants.ARTISTPROFILE);

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


        loadImage(imageUrl);
    }

    private void loadImage(String imageUrl) {
        new DownloadImagesAsync(imageUrl, 0, new DownloadImagesAsync.ImageLoadCallBack() {
            @Override
            public void onImageLoaded(Bitmap image, int position) {
                binding.ivArtistProfile.setImageBitmap(image);
            }
        }).execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
