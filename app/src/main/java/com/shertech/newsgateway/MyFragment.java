package com.shertech.newsgateway;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shertech.newsgateway.MainActivity;
import com.shertech.newsgateway.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFragment extends Fragment {
    private static MainActivity mainActivity;//test this class
    ImageView ivPic;
    private String url ;
    private String TAG="MyFragment";
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final MyFragment newInstance(String message,MainActivity mainActivity1)
    {
        mainActivity = mainActivity1;
        MyFragment f = new MyFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SimpleDateFormat simpleDateFormat;
        Log.d(TAG, "onCreateView: ***********");
        String message = getArguments().getString(EXTRA_MESSAGE);
        View v = inflater.inflate(R.layout.myfragment_layout, container, false);
        try{
            Log.d("mYF", "input ="+message);
            JSONObject jsonData = new JSONObject(message);
            TextView tvTitle = (TextView)v.findViewById(R.id.tvTitle);
            tvTitle.setText(jsonData.getString("title"));

            TextView  tvDescription= (TextView)v.findViewById(R.id.tvDescription);
            tvDescription.setText(jsonData.getString("description"));
            TextView tvAuthor = (TextView)v.findViewById(R.id.tvAuthor);
            String a=jsonData.getString("author");
            if(a.contains(","))
            a=a.substring(0,a.indexOf(","))+"\n"+a.substring(a.indexOf(","));
            tvAuthor.setText("Author :\n"+a);
            TextView tvPageCount = (TextView)v.findViewById(R.id.tvPagecount);
            tvPageCount.setText(jsonData.getString("Count"));
            TextView tvDate = (TextView)v.findViewById(R.id.tvDate);
            Log.d(TAG, "onCreateView: "+tvAuthor.getText().toString()+tvTitle.getText().toString());
            String DATE_FORMAT = jsonData.getString("publishedAt");
            String input = DATE_FORMAT.replace("T", " ");
            String input2 = input.replace("Z", "");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd h:mm:ss");
            SimpleDateFormat format2 = new SimpleDateFormat("MMM dd,yyyy HH:mm");
            try {
                if (DATE_FORMAT!="null"){
                Date date = formatter.parse(input2);
                Log.d("DATE", "DATE12 " + date + " \n" + format2.format(date));
                DATE_FORMAT = format2.format(date);
                    Log.d(TAG, "onCreateView: "+DATE_FORMAT);
                    DATE_FORMAT=DATE_FORMAT.substring(0,DATE_FORMAT.lastIndexOf(" "))+"\n"+DATE_FORMAT.substring(DATE_FORMAT.lastIndexOf(" "));
                    tvDate.setText("Published at :\n"+DATE_FORMAT);}else{
                    Log.d(TAG, "onCreateView: "+DATE_FORMAT);
                    DATE_FORMAT="null";
                    tvDate.setText("Published at :\n"+DATE_FORMAT);

                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            //String df= DATE_FORMAT.substring(0,DATE_FORMAT.indexOf("T"));


            ivPic = (ImageView)v.findViewById(R.id.ivPic);
            loadImage(jsonData.getString("urlToImage"));
            url = jsonData.getString("url");
            tvTitle.setOnClickListener(new TextView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
            tvAuthor.setOnClickListener(new TextView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
            tvDate.setOnClickListener(new TextView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
            tvDescription.setOnClickListener(new TextView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
            tvPageCount.setOnClickListener(new TextView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
            ivPic.setOnClickListener(new ImageView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("crash", "input MF =");
            return null;
        }

        return v;
    }

    private void loadImage(final String imageURL) {
        //     compile 'com.squareup.picasso:picasso:2.5.2'
        if (imageURL.equals(""))
            return;
        //network();
        {
            //   return;
        }

        Log.d(TAG, "loadImage: " + imageURL);

        Picasso picasso = new Picasso.Builder(mainActivity)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.d(TAG, "onImageLoadFailed: ");
                        picasso.load(R.drawable.placeholder)
                                .into(ivPic);
                    }
                })
                .build();
        picasso.load(imageURL)
                .error(R.drawable.brokenimage)
                .placeholder(R.drawable.placeholder)
                .into(ivPic);
    }

}
