package com.imerchantech.michaeljackson.songs_list.contract;

import com.imerchantech.michaeljackson.songs_list.entity.SongsEntity;
import com.imerchantech.michaeljackson.utils.mvp.BasePresenter;
import com.imerchantech.michaeljackson.utils.mvp.BaseView;
import com.imerchantech.michaeljackson.utils.mvp.LoadCallback;

import java.util.List;

public interface SongsContract {

    interface View extends BaseView {
        void showProgress(Boolean show);

        void showSongsList(Boolean show);

        void showError(Boolean show,Boolean error);

        void setSongsList(List<SongsEntity> so);

        void showSongDetails(SongsEntity superHero);

    }

    interface Presenter extends BasePresenter {
        void getSongs();

        void setView(SongsContract.View view);

        void onSongClick(int position);

    }

    interface Model {
        void fetchSongs(LoadCallback<List<SongsEntity>> loadCallback);

        SongsEntity getSongDetailsAtPosition(int position);
    }


}
