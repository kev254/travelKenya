package com.example.tourme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class TaxiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi);

        final String [] cabs={"http://www.uber.com","http://www.bolt.eu/en-ke/cities/nairobi/","http://www.little.bz","http://www.delightcabs.co.ke","http://www.absolutecabs.co.ke","http://www.pewin.co.ke","info@jimcab.co.ke","http://www.kenatco.co.ke","http://www.universalcabs.co.ke","http://www.aircab.co.ke","http://www.princesstravelskenya.com"};

        ListAdapter myAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cabs);
        final ListView myList=(ListView)findViewById(R.id.mylist);

        myList.setAdapter(myAdapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(TaxiActivity.this,web.class);
                String x=parent.getItemAtPosition(position).toString();
                intent.putExtra("transfered",x);
                startActivity(intent);

            }
        });

    }
}
