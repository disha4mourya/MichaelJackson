package com.disha.michaeljackson.songs_list.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.disha.michaeljackson.R;
import com.disha.michaeljackson.songs_list.contract.SongsContract;
import com.disha.michaeljackson.songs_list.entity.SongsEntity;

public class SongsAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private SongsContract.AdapterPresenter presenter;

    SongsAdapter(Context context, SongsContract.AdapterPresenter presenter) {
        this.presenter = presenter;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return presenter.getAdapterCount();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView tvTrackName = vi.findViewById(R.id.tvTrackName); // title
        TextView tvArtist = vi.findViewById(R.id.tvArtist); // artist name
        TextView tvDuration = vi.findViewById(R.id.tvDuration); // duration
        ImageView ivListImage = vi.findViewById(R.id.ivListImage); // thumb image

        SongsEntity songsEntity = presenter.getAdapterEntity(position);


        if (songsEntity.getTrackName() != null) {
            tvTrackName.setText(songsEntity.getTrackName());
        }

        if (songsEntity.getArtistName() != null) {
            tvArtist.setText(songsEntity.getArtistName());
        }

        if (songsEntity.getTrackTimeMillis() != null) {
            String time = millisecondsToTime(Long.parseLong(songsEntity.getTrackTimeMillis()));
            tvDuration.setText(time);
        }

        // imageLoader.DisplayImage(songsEntity.getArtworkUrl100(), ivListImage);

        ivListImage.setImageBitmap(songsEntity.getImageBitmap());

        return vi;
    }

    private String millisecondsToTime(long milliseconds) {
        long minutes = (milliseconds / 1000) / 60;
        long seconds = (milliseconds / 1000) % 60;
        String secondsStr = Long.toString(seconds);
        String secs;
        if (secondsStr.length() >= 2) {
            secs = secondsStr.substring(0, 2);
        } else {
            secs = "0" + secondsStr;
        }
        return minutes + ":" + secs;
    }

}