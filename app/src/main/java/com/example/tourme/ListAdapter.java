package com.example.tourme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
    private String[] titles;
    private String[] urls;
    private Context context;
    private LayoutInflater inflater;

    public ListAdapter(String[] titles, String[] urls, Context context) {
        this.titles = titles;
        this.urls = urls;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.list_item,null,false);
        TextView title=convertView.findViewById(R.id.title);
        TextView url=convertView.findViewById(R.id.url);

        title.setText(titles[position]);
        url.setText(urls[position]);
        return convertView;
    }
}
