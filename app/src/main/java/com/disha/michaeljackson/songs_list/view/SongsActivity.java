package com.disha.michaeljackson.songs_list.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.disha.michaeljackson.R;
import com.disha.michaeljackson.databinding.ActivitySongBinding;
import com.disha.michaeljackson.song_detail.view.SongDetailsActivity;
import com.disha.michaeljackson.songs_list.contract.SongsContract;
import com.disha.michaeljackson.songs_list.entity.SongsEntity;
import com.disha.michaeljackson.songs_list.presenter.SongsPresenter;
import com.disha.michaeljackson.utils.Constants;

public class SongsActivity extends AppCompatActivity implements SongsContract.View {

    private ActivitySongBinding binding;
    private Context context;
    private SongsPresenter presenter;
    private SongsAdapter songsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_song);
        context = this;

        presenter = createPresenter();
        songsAdapter = new SongsAdapter(this, presenter);
        binding.lvSongs.setAdapter(songsAdapter);

        binding.lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                presenter.onSongClick(position);
            }
        });
    }

    private SongsPresenter createPresenter() {


            presenter = new SongsPresenter(this);
            presenter.getSongs();

        return presenter;
    }

    @Override
    public void showProgress(Boolean show) {
        binding.pbLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showSongsList(Boolean show) {
        binding.lvSongs.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(Boolean show, Boolean error) {
        binding.rlError.setVisibility(show ? View.VISIBLE : View.GONE);
        binding.tvError.setVisibility(error ? View.VISIBLE : View.GONE);
        binding.tvEmpty.setVisibility(error ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showSongDetails(SongsEntity songsEntity) {
        Intent intent = new Intent(context, SongDetailsActivity.class);
        intent.putExtra(Constants.ARTISTIMAGE, songsEntity.getArtworkUrl100());
        intent.putExtra(Constants.ARTISTNAME, songsEntity.getArtistName());
        intent.putExtra(Constants.TRACKNAME, songsEntity.getTrackName());
        intent.putExtra(Constants.TRACKPRICE, songsEntity.getTrackPrice());
        intent.putExtra(Constants.COLLECTIONNAME, songsEntity.getCollectionName());
        intent.putExtra(Constants.COLLECTIONPRICE, songsEntity.getCollectionPrice());
        intent.putExtra(Constants.COLLECTIONURL, songsEntity.getCollectionViewUrl());
        intent.putExtra(Constants.ARTISTPROFILE, songsEntity.getArtistViewUrl());
        startActivity(intent);
    }

    @Override
    public void notifySongsData() {
        songsAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyImageLoaded(int position, Bitmap bitmap) {
        View v = binding.lvSongs.getChildAt(position -
                binding.lvSongs.getFirstVisiblePosition());

        if (v == null)
            return;

        ImageView imageView = v.findViewById(R.id.ivListImage);
        imageView.setImageBitmap(bitmap);

    }
}
