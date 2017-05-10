package com.shertech.newsgateway;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lastwalker on 5/2/17.
 */

public class NewsSourceDownloader extends AsyncTask<String, Void, String> {
    private MainActivity mainActivity;
    private String newsCategory,CD;
    private JSONArray jsonarray = new JSONArray();
    private final String dataURL = "https://newsapi.org/v1/sources?language=en&country=us&";
    private final String aPIKey = "7cb387ad1f3a48e39fa1aa90fb9d7968";
    private static final String TAG = "NewsSourceDownloader";

    public NewsSourceDownloader(MainActivity mainActivity,String newsCategory) {
        this.mainActivity = mainActivity;
        this.newsCategory=newsCategory;
        if (newsCategory.equals("all")){
            CD="";
        }else if (newsCategory==""){
            CD="";
        }else {
            CD=this.newsCategory;
        }
    }

    @Override
    protected String doInBackground(String... params) {
        Uri.Builder buildURL = Uri.parse(dataURL).buildUpon();
        buildURL.appendQueryParameter("apiKey", aPIKey);
            buildURL.appendQueryParameter("category", CD);
        String urlToUse = buildURL.build().toString();

        Log.d(TAG, "doInBackground: S" + urlToUse);
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int statusCode = conn.getResponseCode();
            if(statusCode!=200)
            {
                return null;
            }
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: S1", e);
            return null;
        }
        return sb.toString();
    }
    private ArrayList<CategoryData> parseJSON(String s) {
        Log.d(TAG, "parseJSON: S");
        if(s==null)
            return null;
        ArrayList<CategoryData> newsCategories = new ArrayList<>();
        newsCategories.clear();
        try {
            JSONObject govOffice = new JSONObject(s);
            JSONArray offices_name = govOffice.getJSONArray("sources");

            int count = 0;
            while (count < offices_name.length()) {
                JSONObject officeObject = offices_name.getJSONObject(count);
                count++;
                String id = officeObject.getString("id");
                String name = officeObject.getString("name");
                String url = officeObject.getString("url");
                String category = officeObject.getString("category");
                newsCategories.add(new CategoryData(id, name,url,category));
                Log.d("Data", "Id =" + id+" name: "+name+" url: "+url+" Category: "+category);
            }
            return newsCategories;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("crash", "****************");
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: S");
        if (s==null)
            return;
        ArrayList<CategoryData> newsCategories = parseJSON(s);
        mainActivity.setSource(newsCategories);
        super.onPostExecute(s);
    }
}
