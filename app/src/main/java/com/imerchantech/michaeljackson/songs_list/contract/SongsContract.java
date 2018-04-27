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

        void showError(Boolean show);

        void setSongsList(List<SongsEntity> so);

    }

    interface Presenter extends BasePresenter {
        void getSongs();

        void setView(SongsContract.View view);

    }

    interface Model {
        void fetchSongs(LoadCallback<List<SongsEntity>> loadCallback);
    }


}
