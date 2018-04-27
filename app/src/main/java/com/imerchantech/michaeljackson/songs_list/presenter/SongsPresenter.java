package com.imerchantech.michaeljackson.songs_list.presenter;

import com.imerchantech.michaeljackson.songs_list.contract.SongsContract;
import com.imerchantech.michaeljackson.songs_list.entity.SongsEntity;
import com.imerchantech.michaeljackson.songs_list.model.SongsModel;
import com.imerchantech.michaeljackson.utils.mvp.LoadCallback;

import java.util.List;

public class SongsPresenter implements SongsContract.Presenter {

    private SongsContract.View view = null;
    private SongsContract.Model model;

    public SongsPresenter(SongsContract.View view, SongsContract.Model model) {
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
        view.showError(false);
        model.fetchSongs(new LoadCallback<List<SongsEntity>>() {
            @Override
            public void onSuccess(List<SongsEntity> response) {
                view.setSongsList(response);
                view.showSongsList(true);
                view.showError(false);
                view.showProgress(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                view.showSongsList(false);
                view.showError(true);
                view.showProgress(false);
            }

        });
    }

    @Override
    public void setView(SongsContract.View view) {

    }


    @Override
    public void create() {

    }
}
