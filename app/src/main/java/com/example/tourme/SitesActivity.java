package com.example.tourme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class SitesActivity extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites);
        searchView= findViewById(R.id.searchView);
        listView=findViewById(R.id.Iv1);

        list=new ArrayList<>();
        list.add("Amboseli");
        list.add("Mara");
        list.add("Nairobi");
        list.add("Mt. Kenya");
        list.add("Suswa");
        list.add("Impala");
        list.add("Tsavo");
        list.add("Hells Gate");
        list.add("Kisii");
        list.add("Kenya");

        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,list);
        listView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }
                Toast.makeText(SitesActivity.this,"No match Result",Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
