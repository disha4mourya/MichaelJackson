package com.imerchantech.michaeljackson.songs_list.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.imerchantech.michaeljackson.R;
import com.imerchantech.michaeljackson.databinding.ActivitySongBinding;
import com.imerchantech.michaeljackson.songs_list.contract.SongsContract;
import com.imerchantech.michaeljackson.songs_list.entity.SongsEntity;
import com.imerchantech.michaeljackson.songs_list.presenter.SongsPresenter;
import com.imerchantech.michaeljackson.utils.mvp.PresenterHolder;

import java.util.List;

public class SongsActivity extends AppCompatActivity implements SongsContract.View {

    ActivitySongBinding binding;
    private Context context;
    private SongsPresenter presenter;
    SongsAdapter songsAdapter;
    private static final int REQUEST = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_song);
        context = this;
        askPermission();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = createPresenter();
        presenter.create();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.isFinishing()) {
            PresenterHolder.getInstance().remove(SongsActivity.class);
        }
    }

    private SongsPresenter createPresenter() {

        SongsPresenter presenter = PresenterHolder.getInstance().getPresenter(SongsPresenter.class);
        if (presenter != null) {
            presenter.setView(this);
            presenter.getSongs();
        } else {
            presenter = new SongsPresenter(this);
            PresenterHolder.getInstance().putPresenter(SongsPresenter.class, presenter);
            presenter.getSongs();

        }
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
    public void showError(Boolean show) {
        binding.rlError.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setSongsList(List<SongsEntity> songsEntityList) {
        songsAdapter = new SongsAdapter(this, songsEntityList);
        binding.lvSongs.setAdapter(songsAdapter);
    }


    public void askPermission() {

        if (Build.VERSION.SDK_INT >= 23) {

            String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (!hasPermissions(context, PERMISSIONS)) {
                ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, REQUEST);
            }
        } else {
            presenter.getSongs();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.getSongs();
                } else {
                    presenter.getSongs();
                    Toast.makeText(context, "No permission to write in your storage.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
