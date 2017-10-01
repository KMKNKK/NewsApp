package com.imooc.muju_md;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.imooc.muju_md.SQL.LoginActivity;
import com.imooc.muju_md.SQL.News_Collection;

public class ContentActivity extends AppCompatActivity {
    private WebView webView;
    private int A = 0;
    private boolean C=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }

        webView = (WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        final String uri = getIntent().getStringExtra("uri");
        final String title = getIntent().getStringExtra("title");
        final String dec = getIntent().getStringExtra("dec");
        final String pic = getIntent().getStringExtra("pic");
        final String id = getIntent().getStringExtra("id");
        final int login = getIntent().getIntExtra("login",0);

        System.out.println(""+login);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
        if(uri.equals("http://47.94.86.217/main.html"))
        {
            actionBar.setTitle("首页");
        }
        webView.loadUrl(uri);
        System.out.println("uri:"+uri);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new Thread(){
                        public void run(){
                            News_Collection N = new News_Collection(dec,uri,pic);
                            System.out.println(id);
                            if (login == 0){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ContentActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Intent i = new Intent(ContentActivity.this,LoginActivity.class);
                                startActivity(i);
                            }
                            else{
                                if(N.insertNewsCollection(id)){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ContentActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ContentActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }
                    }.start();





            }
        });
    }

    /**
     * 点击返回键做了处理
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
}
