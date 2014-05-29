package com.shirwa.rssdemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.shirwa.simplistic_rss.RssItem;
import com.shirwa.simplistic_rss.RssReader;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ListView mList;
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mList = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this, R.layout.basic_list_item);
        new GetRssFeed().execute("http://feeds.bbci.co.uk/news/rss.xml");
    }

    private class GetRssFeed extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                RssReader rssReader = new RssReader(params[0]);
                for (RssItem item : rssReader.getItems())
                    adapter.add(item.getTitle());
            } catch (Exception e) {
                Log.v("Error Parsing Data", e + "");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
            mList.setAdapter(adapter);
        }
    }

}
