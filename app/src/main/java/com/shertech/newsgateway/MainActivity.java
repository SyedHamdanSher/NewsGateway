package com.shertech.newsgateway;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.support.v4.view.PagerAdapter.POSITION_NONE;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    static final String ACTION_MSG_TO_SERVICE = "ACTION_MSG_TO_SERVICE";
    static final String ACTION_NEWS_STORY = "ACTION_NEWS_STORY";
    String flag = "";
    String pos="no";
    static final String DATA_EXTRA2 = "DATA_EXTRA2";
    static final String DATA_EXTRA3 = "DATA_EXTRA3";
    NewsService ns;
    Intent intentx;

    NewsReceiver newsReceiver;
    ViewPager viewPager;

    private MyPageAdapter pageAdapter;
    private List<Fragment> fragments;
    private ViewPager pager;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<String> newsItems = new ArrayList<>();
    private ArrayList<CategoryData> newsCategories = new ArrayList<>();
    private ArrayList<NewData> newsData = new ArrayList<>();
    HashMap hashMapCategory = new HashMap();
    String title="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentx = new Intent(MainActivity.this, NewsService.class);
        startService(intentx);
        newsReceiver = new NewsReceiver();

        IntentFilter filter1 = new IntentFilter(MainActivity.ACTION_NEWS_STORY);
        registerReceiver(newsReceiver, filter1);

       /* newsItems.add("CNN");
        newsItems.add("Newsweek");
        newsItems.add("BuzzFeed");*/

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list, newsItems));
        mDrawerList.setOnItemClickListener(
                new ListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectItem(position);
                    }
                }
        );

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //

        fragments = getFragments();

        pageAdapter = new MyPageAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);
        if(network()==1)
        new NewsSourceDownloader(this,"").execute();

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.allID:
                flag=item.toString();
                if(network()==1)
                new NewsSourceDownloader(this,item.toString()).execute();
                break;

            case R.id.scienceID:
                flag=item.toString();
                if(network()==1)
                new NewsSourceDownloader(this,item.toString()).execute();
                break;
            case R.id.gameID:
                flag=item.toString();
                if(network()==1)
                new NewsSourceDownloader(this,item.toString()).execute();
                break;
            case R.id.techID:
                flag=item.toString();
                if(network()==1)
                new NewsSourceDownloader(this,item.toString()).execute();
                break;
            case R.id.bussID:
                flag=item.toString();
                if(network()==1)
                new NewsSourceDownloader(this,item.toString()).execute();
                break;
            case R.id.polID:
                flag=item.toString();
                if(network()==1)
                new NewsSourceDownloader(this,item.toString()).execute();
                break;
            case R.id.genID:
                flag=item.toString();
                if(network()==1)
                new NewsSourceDownloader(this,item.toString()).execute();
                break;
            case R.id.entID:
                flag=item.toString();
                if(network()==1)
                new NewsSourceDownloader(this,item.toString()).execute();
                break;
            case R.id.spID:
                flag=item.toString();
                if(network()==1)
                new NewsSourceDownloader(this,item.toString()).execute();
                break;
            case R.id.muID:
                flag=item.toString();
                if(network()==1)
                new NewsSourceDownloader(this,item.toString()).execute();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
    

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();
        return fList;
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        private long baseId = 0;


        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public long getItemId(int position) {
            // give an ID different from position when position has been changed
            return baseId + position;
        }

        /**
         * Notify that the position of a fragment has been changed.
         * Create a new ID for each position to force recreation of the fragment
         * @param n number of newsItems which have been changed
         */
        public void notifyChangeInPosition(int n) {
            // shift the ID returned by getItemId outside the range of all previous fragments
            baseId += getCount() + n;
        }

    }
    public void setSource(ArrayList<CategoryData> cList) {
        Log.d(TAG, "setSource: ");
        hashMapCategory.clear();
        newsItems.clear();
        if(cList.size()>0) {
            for (int i = 0; i < cList.size(); i++) {
                newsItems.add(cList.get(i).getName());
                Log.d(TAG, "news Name : " + cList.get(i).getName());
                hashMapCategory.put(cList.get(i).getName(),cList.get(i));
            }
            if (newsCategories==null)
            {
                newsCategories.addAll(cList);
            }

            mDrawerList.setAdapter(new ArrayAdapter<>(this,
                    R.layout.drawer_list, newsItems));
            mDrawerList.setOnItemClickListener(
                    new ListView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(network()==1)
                            selectItem(position);
                            else
                                mDrawerLayout.closeDrawer(mDrawerList);
                        }
                    }
            );
        }
    }
    private void selectItem(int position) {
        Log.d(TAG, "selectItem: ");
        Toast.makeText(this, newsItems.get(position), Toast.LENGTH_SHORT).show();
        pager.setBackground(null);//here
        title=newsItems.get(position);
        setTitle(newsItems.get(position));
        Intent intent = new Intent();
        intent.setAction(ACTION_MSG_TO_SERVICE);
        CategoryData cList= (CategoryData) hashMapCategory.get(newsItems.get(position));
        Log.d(TAG, "selectItem: ADS"+cList.getId());
        pos=cList.getId();
        intent.putExtra(DATA_EXTRA2, cList.getId());
        intent.putExtra(DATA_EXTRA3, (Serializable) hashMapCategory.get(newsItems.get(position)));
        sendBroadcast(intent);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    class NewsReceiver extends BroadcastReceiver {
        ArrayList<NewData> ARTICLELIST ;
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case MainActivity.ACTION_NEWS_STORY:
                    if (intent.hasExtra("AL"))
                        ARTICLELIST = (ArrayList<NewData>) intent.getSerializableExtra("AL");
                    break;
            }
            if (!ARTICLELIST.isEmpty()){
            redoF(ARTICLELIST);}
        }
        private void redoF(ArrayList<NewData> AL){
            Log.d(TAG, "redoF: ");
            setTitle(title);
            for (int I=0;I<pageAdapter.getCount();I++){
                pageAdapter.notifyChangeInPosition(I);
            }
            fragments.clear();
            for (int i = 0; i < AL.size(); i++) {
                String s1 = new String(AL.get(i).getData().substring(0,AL.get(i).getData().length()-1));
                s1 = s1+",\"Count\":\""+(i+1)+" of "+AL.size()+"\"}";
                Log.d(TAG,"NEWDATA:"+s1);
                fragments.add(MyFragment.newInstance(s1,MainActivity.this));
            }
            pageAdapter.notifyDataSetChanged();
            pager.setCurrentItem(0);
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");
        Log.d(TAG, "onSaveInstanceState: "+flag+"\n"+pos);
        outState.putString("category", flag);
        outState.putString("position", pos);
        outState.putString("title", title);
        /*if(pager.getBackground().equals(null)){
            outState.putBoolean("pager", true);
        }else
            outState.putBoolean("pager", true);*/
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: ");
        super.onRestoreInstanceState(savedInstanceState);
        flag =savedInstanceState.getString("category");
        pos =savedInstanceState.getString("position");
        title =savedInstanceState.getString("title");
        if(network()==1)
            new NewsSourceDownloader(this,flag).execute();
        if(network()==1){
            if (!pos.equals("no")){
                pager.setBackground(null);
                setTitle(title);
                Intent intent = new Intent();
                intent.setAction(ACTION_MSG_TO_SERVICE);
                intent.putExtra(DATA_EXTRA2, pos);
                sendBroadcast(intent);
                sendBroadcast(intent);}}

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        //unregisterReceiver(newsReceiver);
        //stopService(intentx);
    }
}
