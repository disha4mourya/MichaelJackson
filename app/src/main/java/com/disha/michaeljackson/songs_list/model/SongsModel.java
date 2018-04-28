package com.disha.michaeljackson.songs_list.model;

import android.os.AsyncTask;

import com.disha.michaeljackson.songs_list.contract.SongsContract;
import com.disha.michaeljackson.songs_list.entity.SongsEntity;
import com.disha.michaeljackson.utils.Constants;
import com.disha.michaeljackson.utils.DownloadImagesAsync;
import com.disha.michaeljackson.utils.mvp.LoadCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.disha.michaeljackson.utils.Constants.CONNECTION_TIMEOUT;
import static com.disha.michaeljackson.utils.Constants.READ_TIMEOUT;

public class SongsModel implements SongsContract.Model {

    private List<SongsEntity> songsEntityList;

    public SongsModel() {
        songsEntityList = new ArrayList<>();
    }

    @Override
    public void fetchSongs(final LoadCallback<List<SongsEntity>> loadCallback) {
        new AsyncRetrieve(new LoadCallback<List<SongsEntity>>() {
            @Override
            public void onSuccess(List<SongsEntity> songsEntityList) {
                loadCallback.onSuccess(songsEntityList);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        }).execute();
    }

    @Override
    public void setSongsEntityList(List<SongsEntity> songsEntityList) {
        this.songsEntityList = songsEntityList;
    }

    @Override
    public List<SongsEntity> getSongsEntityList() {
        return songsEntityList;
    }

    @Override
    public void downLoadImage(String imageUrl, int position, DownloadImagesAsync.ImageLoadCallBack loadCallBack) {
        new DownloadImagesAsync(imageUrl, position, loadCallBack).execute();
    }

    /**
     * fetch list of songs from server asynchronously
     */
    private static class AsyncRetrieve extends AsyncTask<String, String, String> {
        HttpURLConnection conn;
        URL url = null;
        LoadCallback<List<SongsEntity>> loadCallback;

        //constructor to pass callback response
        AsyncRetrieve(LoadCallback<List<SongsEntity>> loadCallback) {
            this.loadCallback = loadCallback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(Constants.SONGS_LIST_URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);

            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            if (!result.equals("")) {
                List<SongsEntity> songsEntityList = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        SongsEntity songsEntity = new SongsEntity();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        if (jsonObject1.has("trackName")) {
                            songsEntity.setTrackName(jsonObject1.getString("trackName"));
                        }
                        if (jsonObject1.has("collectionName")) {
                            songsEntity.setCollectionName(jsonObject1.getString("collectionName"));
                        }

                        if (jsonObject1.has("artworkUrl100")) {
                            songsEntity.setArtworkUrl100(jsonObject1.getString("artworkUrl100"));
                        }

                        if (jsonObject1.has("trackTimeMillis")) {
                            songsEntity.setTrackTimeMillis(jsonObject1.getString("trackTimeMillis"));
                        }

                        if (jsonObject1.has("artistName")) {
                            songsEntity.setArtistName(jsonObject1.getString("artistName"));
                        }
                        if (jsonObject1.has("collectionPrice")) {
                            songsEntity.setCollectionPrice(jsonObject1.getString("collectionPrice"));
                        }
                        if (jsonObject1.has("trackPrice")) {
                            songsEntity.setTrackPrice(jsonObject1.getString("trackPrice"));
                        }
                        if (jsonObject1.has("releaseDate")) {
                            songsEntity.setReleaseDate(jsonObject1.getString("releaseDate"));
                        }
                        if (jsonObject1.has("trackCensoredName")) {
                            songsEntity.setTrackCensoredName(jsonObject1.getString("trackCensoredName"));
                        }
                        if (jsonObject1.has("collectionViewUrl")) {
                            songsEntity.setCollectionViewUrl(jsonObject1.getString("collectionViewUrl"));
                        }
                        if (jsonObject1.has("artistViewUrl")) {
                            songsEntity.setArtistViewUrl(jsonObject1.getString("artistViewUrl"));
                        }
                        songsEntityList.add(songsEntity);
                    }

                    loadCallback.onSuccess(songsEntityList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
