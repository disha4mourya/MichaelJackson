package com.imerchantech.michaeljackson.songs_list.view;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.imerchantech.michaeljackson.R;
import com.imerchantech.michaeljackson.databinding.ActivitySongBinding;
import com.imerchantech.michaeljackson.songs_list.contract.SongsContract;
import com.imerchantech.michaeljackson.songs_list.presenter.SongsPresenter;
import com.imerchantech.michaeljackson.utils.mvp.PresenterHolder;

public class SongsActivity extends AppCompatActivity implements SongsContract.View {

    ActivitySongBinding binding;
    private Context context;
    private SongsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_song);
        context=this;
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
        SongsPresenter presenter = PresenterHolder.getInstance().getPresenter
                (SongsPresenter.class);
        if (presenter != null) {
            presenter.setView(this);
        } else {
            presenter = new SongsPresenter(this);
            PresenterHolder.getInstance().putPresenter(SongsPresenter.class, presenter);
        }
        return presenter;
    }
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void openActivity(Class<? extends Activity> classname) {

    }

    @Override
    public void showToast(String message) {

    }
}
