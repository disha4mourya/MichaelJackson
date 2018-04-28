package com.imerchantech.michaeljackson.songs_list.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imerchantech.michaeljackson.R;
import com.imerchantech.michaeljackson.songs_list.entity.SongsEntity;
import com.imerchantech.michaeljackson.utils.image_loading.ImageLoader;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

       // imageLoader.DisplayImage(songsEntity.getArtworkUrl100(), ivListImage);

        ivListImage.setTag(songsEntity.getArtworkUrl100());
        new DownloadImagesTask().execute(ivListImage);
        return vi;
    }

    public class DownloadImagesTask extends AsyncTask<ImageView, Void, Bitmap> {

        ImageView imageView = null;

        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
            this.imageView = imageViews[0];
            return download_Image((String) imageView.getTag());
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
        private Bitmap download_Image(String url) {

            Bitmap bmp =null;
            try{
                URL ulrn = new URL(url);
                HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
                InputStream is = con.getInputStream();
                bmp = BitmapFactory.decodeStream(is);
                if (null != bmp)
                    return bmp;

            }catch(Exception e){}
            return bmp;
        }

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