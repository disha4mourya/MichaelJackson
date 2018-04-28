package com.disha.michaeljackson.songs_list.contract;

import android.graphics.Bitmap;

import com.disha.michaeljackson.songs_list.entity.SongsEntity;
import com.disha.michaeljackson.utils.DownloadImagesAsync;
import com.disha.michaeljackson.utils.mvp.LoadCallback;

import java.util.List;

public interface SongsContract {

    interface View {
        void showProgress(Boolean show);

        void showSongsList(Boolean show);

        void showError(Boolean show, Boolean error);

        void showSongDetails(SongsEntity superHero);

        void notifySongsData();

        void notifyImageLoaded(int position, Bitmap bitmap);
    }

    interface Presenter {
        void getSongs();

        void setView(SongsContract.View view);

        void onSongClick(int position);
    }

    interface AdapterPresenter {
        int getAdapterCount();

        SongsEntity getAdapterEntity(int position);
    }

    interface Model {
        void fetchSongs(LoadCallback<List<SongsEntity>> loadCallback);

        void setSongsEntityList(List<SongsEntity> songsEntityList);

        List<SongsEntity> getSongsEntityList();

        void downLoadImage(String imageUrl, int position, DownloadImagesAsync.ImageLoadCallBack loadCallBack);
    }
}
