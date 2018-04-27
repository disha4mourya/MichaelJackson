package com.imerchantech.michaeljackson.songs_list.presenter;

import com.imerchantech.michaeljackson.songs_list.contract.SongsContract;
import com.imerchantech.michaeljackson.songs_list.model.SongsModel;

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

    }

    @Override
    public void setView(SongsContract.View view) {

    }

    @Override
    public void create() {

    }
}
