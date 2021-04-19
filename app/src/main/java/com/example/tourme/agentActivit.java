package com.example.tourme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class agentActivit extends AppCompatActivity {

    private String[] titles;
    private String[] urls;
    private ListView listView;
   // ViewFlipper imgFlipper;
    private WebView webView;
    private ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);

        /*imgFlipper=findViewById(R.id.imagFlpper);

        imgFlipper.setFlipInterval(2500);
        imgFlipper.setAutoStart(true);
        Animation inAnimation= AnimationUtils.loadAnimation(agentActivit.this,R.anim.animation_in);
        Animation outAnimation=AnimationUtils.loadAnimation(agentActivit.this,R.anim.animation_out);
        imgFlipper.setInAnimation(inAnimation);
        imgFlipper.setOutAnimation(outAnimation);

        imgFlipper.startFlipping();*/

        titles=new String[]
                {"Expoler Kenya tours and travel Lmtd","Amicabre travel services Ltd","Go Kenya tours and safari","Nahdy travel and tours","Asili adventures and tours","Cruzeiro safaris Kenya","Go Kenya","go Africa","Spot Kenya","Speedbird travel","Dream world"};
        urls=new String[]
                {"http://www.explorerkenya.com","http://www.amicabretravel.com","http://www.gokenyasafari.com","https://www.nahdytravel.com","https://www.asiliadventuresafaris.com/","https://www.cruzeiro-safaris.com/","https://www.acharyatravel.com/","https://www.go-africa-safaris.com/","https://www.spotkenyasafaris.com/","http://www.speedbirdsafaris.com/","https://www.dreamzworldgroup.com/\n"};

        listView=findViewById(R.id.listview);
        webView=findViewById(R.id.webview);

        adapter=new ListAdapter(titles,urls,agentActivit.this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                  TextView textView =view.findViewById(R.id.url);
                  String uri=textView.getText().toString();
                  if (!TextUtils.isEmpty(uri)){
                      setUpWebView(uri);
                  }

            }
        });

    }

    private void setUpWebView(String uri) {
        webView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new Client());
        webView.loadUrl(uri);
    }

  class Client extends WebViewClient{
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
          return false;
      }
  }
}
