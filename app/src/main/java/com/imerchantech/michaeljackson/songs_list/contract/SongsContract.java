package com.imerchantech.michaeljackson.songs_list.contract;

import android.app.Activity;

import com.imerchantech.michaeljackson.songs_list.entity.SongsEntity;
import com.imerchantech.michaeljackson.utils.mvp.BasePresenter;
import com.imerchantech.michaeljackson.utils.mvp.BaseView;

import java.util.List;

public interface SongsContract {

    interface  View extends BaseView{
        void showProgress();

        void hideProgress();

        void openActivity(Class<? extends Activity> classname);

        void showToast(String message);
    }

    interface Presenter extends BasePresenter{
        void getSongs();
        void setView(SongsContract.View view);
    }

    interface Model  {
        void fetchSongs(List<SongsEntity> songsEntityList);
    }


}
