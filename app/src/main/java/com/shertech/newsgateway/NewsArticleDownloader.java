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

import static android.content.ContentValues.TAG;

/**
 * Created by lastwalker on 5/2/17.
 */

public class NewsArticleDownloader extends AsyncTask<String, Void, String> {
    private JSONArray jsonarray = new JSONArray();

    private final String dataURL = "https://newsapi.org/v1/articles?";
    private final String aPIKey = "7cb387ad1f3a48e39fa1aa90fb9d7968";
    private static final String TAG = "NewsArticleDownloader";
    NewsService ns=new NewsService();
    String S="";
    NewsArticleDownloader(NewsService ns,String S){
        this.ns=ns;
        this.S=S;
    }

    @Override
    protected String doInBackground(String... params) {
        Uri.Builder buildURL = Uri.parse(dataURL).buildUpon();
        buildURL.appendQueryParameter("apiKey", aPIKey);
        buildURL.appendQueryParameter("source", S);
        String urlToUse = buildURL.build().toString();

        Log.d(TAG, "doInBackground: " + urlToUse);
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
            Log.e(TAG, "doInBackground: 1", e);
            return null;
        }
        Log.d(TAG, "doInBackground: 1"+ sb.toString());
        return sb.toString();
    }

    private ArrayList<NewData> parseJSON(String s) {
        Log.d(TAG, "parseJSON: A");
        if(s==null)
            return null;
        ArrayList<NewData> newsResults = new ArrayList<>();
        newsResults.clear();
        try {
            JSONObject govOffice = new JSONObject(s);
            //   jsonarray = new JSONArray(s);
            String source=govOffice.getString("source");
            JSONArray offices_name = govOffice.getJSONArray("articles");
            int count = 0;
            while (count < offices_name.length()) {
                JSONObject officeObject = offices_name.getJSONObject(count);
                count++;
                String author = officeObject.getString("author");
                String title = officeObject.getString("title");
                String description = officeObject.getString("description");
                String urlToImage = officeObject.getString("urlToImage");
                String publishedAt = officeObject.getString("publishedAt");
                String url = officeObject.getString("url");
                String data = officeObject.toString();
                newsResults.add(new NewData(author, title,description,urlToImage,publishedAt,data,url));
                Log.d("Data", "author =" + author+" Title: "+title+" url: "+url+" urlImage: "+urlToImage+" PublishedAt: "+publishedAt+" Data: "+data);
            }
            return newsResults;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("crash", "input =AFSFSF");
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: A");
        Log.d(TAG, "Complete: " + s);
        if (s!=null){
        ArrayList<NewData> newsResults = parseJSON(s);
        ns.setArticle(newsResults);}

    }
}
