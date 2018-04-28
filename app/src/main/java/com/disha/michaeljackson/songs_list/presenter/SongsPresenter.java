package com.disha.michaeljackson.songs_list.presenter;

import android.graphics.Bitmap;

import com.disha.michaeljackson.songs_list.contract.SongsContract;
import com.disha.michaeljackson.songs_list.entity.SongsEntity;
import com.disha.michaeljackson.songs_list.model.SongsModel;
import com.disha.michaeljackson.utils.DownloadImagesAsync;
import com.disha.michaeljackson.utils.mvp.LoadCallback;

import java.util.List;

public class SongsPresenter implements SongsContract.Presenter, SongsContract.AdapterPresenter {

    private SongsContract.View view = null;
    private SongsContract.Model model;

    private SongsPresenter(SongsContract.View view, SongsContract.Model model) {
        this.view = view;
        this.model = model;
    }

    public SongsPresenter(SongsContract.View view) {
        this(view, new SongsModel());
    }

    @Override
    public void getSongs() {

        view.showProgress(true);
        view.showSongsList(false);
        view.showError(false, false);
        model.fetchSongs(new LoadCallback<List<SongsEntity>>() {
            @Override
            public void onSuccess(List<SongsEntity> response) {
                model.setSongsEntityList(response);
                if (response.size() > 0) {
                    view.notifySongsData();
                    view.showSongsList(true);
                    view.showError(false, false);
                    view.showProgress(false);
                    updateImage();
                } else {
                    view.showSongsList(false);
                    view.showError(true, false);
                    view.showProgress(false);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                view.showSongsList(false);
                view.showError(true, true);
                view.showProgress(false);
            }

        });
    }

    private void updateImage() {
        for (int i = 0; i < model.getSongsEntityList().size(); i++) {
            SongsEntity current = model.getSongsEntityList().get(i);
            model.downLoadImage(current.getArtworkUrl100(), i, new DownloadImagesAsync.ImageLoadCallBack() {
                @Override
                public void onImageLoaded(Bitmap image, int position) {
                    model.getSongsEntityList().get(position).setImageBitmap(image);
                    view.notifyImageLoaded(position, image);
                }
            });
        }
    }

    @Override
    public void setView(SongsContract.View view) {

    }

    @Override
    public void onSongClick(int position) {
        SongsEntity songsEntity = model.getSongsEntityList().get(position);
        if (songsEntity != null) {
            view.showSongDetails(songsEntity);
        }
    }


    @Override
    public int getAdapterCount() {
        return model.getSongsEntityList().size();
    }

    @Override
    public SongsEntity getAdapterEntity(int position) {
        return model.getSongsEntityList().get(position);
    }
}
