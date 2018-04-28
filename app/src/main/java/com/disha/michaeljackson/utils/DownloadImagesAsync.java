package com.disha.michaeljackson.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImagesAsync extends AsyncTask<Void, Void, Bitmap> {

    private String artworkUrl100;
    private ImageLoadCallBack imageLoadCallBack;
    private int position;

    public DownloadImagesAsync(String artworkUrl100, int position, ImageLoadCallBack imageLoadCallBack) {
        this.artworkUrl100 = artworkUrl100;
        this.position = position;
        this.imageLoadCallBack = imageLoadCallBack;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        return download_Image(artworkUrl100);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        imageLoadCallBack.onImageLoaded(result, position);
    }

    private Bitmap download_Image(String url) {

        try {
            URL ulrn = new URL(url);
            HttpURLConnection con = (HttpURLConnection) ulrn.openConnection();
            InputStream is = con.getInputStream();
            Bitmap bmp = BitmapFactory.decodeStream(is);
            if (null != bmp)
                return bmp;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface ImageLoadCallBack {
        void onImageLoaded(Bitmap image, int position);
    }
}
