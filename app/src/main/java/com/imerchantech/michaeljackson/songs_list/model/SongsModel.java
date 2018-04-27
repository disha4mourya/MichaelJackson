package com.imerchantech.michaeljackson.songs_list.model;

import android.os.AsyncTask;

import com.imerchantech.michaeljackson.songs_list.contract.SongsContract;
import com.imerchantech.michaeljackson.songs_list.entity.SongsEntity;
import com.imerchantech.michaeljackson.utils.Constants;
import com.imerchantech.michaeljackson.utils.mvp.LoadCallback;

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

import static com.imerchantech.michaeljackson.utils.Constants.CONNECTION_TIMEOUT;
import static com.imerchantech.michaeljackson.utils.Constants.READ_TIMEOUT;

public class SongsModel implements SongsContract.Model {

    List<SongsEntity> songsEntityList;

    @Override
    public void fetchSongs(LoadCallback<List<SongsEntity>> loadCallback) {


        new AsyncRetrieve(loadCallback).execute();

        if (songsEntityList != null && songsEntityList.size() > 0)
            loadCallback.onSuccess(songsEntityList);
        else {
            loadCallback.onFailure(new Throwable("Error"));
        }

    }

    private class AsyncRetrieve extends AsyncTask<String, String, String> {
        HttpURLConnection conn;
        URL url = null;
        LoadCallback loadCallback;

        public AsyncRetrieve(LoadCallback loadCallback) {
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
                // TODO Auto-generated catch block
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

            songsEntityList = new ArrayList<>();
            if (!result.equals("")) {
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
                        songsEntityList.add(songsEntity);
                    }

                    if (songsEntityList.size() > 0)
                        loadCallback.onSuccess(songsEntityList);
                    else
                        loadCallback.onFailure(new Throwable("Error"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //textPHP.setText(result.toString());
            } else {
                // you to understand error returned from doInBackground method
            }

        }


    }
}
