package com.imerchantech.michaeljackson.songs_list.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imerchantech.michaeljackson.R;
import com.imerchantech.michaeljackson.songs_list.entity.SongsEntity;
import com.imerchantech.michaeljackson.utils.image_loading.ImageLoader;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SongsAdapter extends BaseAdapter {

    private Activity activity;
    private List<SongsEntity> data;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader;

    public SongsAdapter(Activity a, List<SongsEntity> songsEntities) {
        activity = a;
        data = songsEntities;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView tvTrackName = vi.findViewById(R.id.tvTrackName); // title
        TextView tvArtist = vi.findViewById(R.id.tvArtist); // artist name
        TextView tvDuration = vi.findViewById(R.id.tvDuration); // duration
        ImageView ivListImage = vi.findViewById(R.id.ivListImage); // thumb image

        SongsEntity songsEntity = data.get(position);


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

        imageLoader.DisplayImage(songsEntity.getArtworkUrl100(), ivListImage);

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