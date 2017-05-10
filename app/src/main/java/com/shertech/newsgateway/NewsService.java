package com.shertech.newsgateway;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lastwalker on 5/2/17.
 */

public class NewsService extends Service {
    private static final String TAG = "NewsService";
    private boolean isRunning = true;
    private ServiceReceiver serviceReceiver;
    private ArrayList<NewData> newsResults = new ArrayList<>();

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");

        serviceReceiver = new ServiceReceiver();

        IntentFilter filter1 = new IntentFilter(MainActivity.ACTION_MSG_TO_SERVICE);
        registerReceiver(serviceReceiver, filter1);

        new Thread(new Runnable() {
            @Override
            public void run() {

                //In this example we are just displaying a log message every 1000ms
                while (isRunning) {
                    try {
                            if (newsResults.isEmpty()){
                                //Log.d(TAG, "run: ************"+newsResults.isEmpty());
                                Thread.sleep(250);
                            }else {
                                Intent intent2 = new Intent();
                                intent2.setAction(MainActivity.ACTION_NEWS_STORY);
                                intent2.putExtra("AL",(Serializable)newsResults);
                                sendBroadcast(intent2);
                                newsResults.clear();
                            }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return Service.START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        unregisterReceiver(serviceReceiver);
        isRunning = false;
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();

    }

    class ServiceReceiver extends BroadcastReceiver {
        String sourceID;
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case MainActivity.ACTION_MSG_TO_SERVICE:
                    if (intent.hasExtra(MainActivity.DATA_EXTRA2))
                        Log.d(TAG, "onReceive: SS"+sourceID);
                        sourceID = intent.getStringExtra(MainActivity.DATA_EXTRA2);
                    break;
            }
            if(network()==1)
            new NewsArticleDownloader(NewsService.this,sourceID).execute();
        }
    }
    public void setArticle(ArrayList<NewData> nR){
        Log.d(TAG, "setArticle: "+nR);
        newsResults.clear();
        newsResults.addAll(nR);

    }
    public int network(){
        Log.d(TAG, "network: ");
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (!(netInfo != null && netInfo.isConnectedOrConnecting())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Data cannot be loaded without an internet connection");
            builder.setTitle("No Network Connection");
            AlertDialog dialog = builder.create();
            dialog.show();
            return 0;
        }
        else {
            return 1;
        }

    }

}
