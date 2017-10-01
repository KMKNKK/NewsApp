package com.imooc.muju_md;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.imooc.muju_md.SQL.JDBC_connection;
import com.imooc.muju_md.SQL.LoginActivity;
import com.imooc.muju_md.gson.GsonServer;
import com.imooc.muju_md.gson.NList;
import com.imooc.muju_md.gson.News;
import com.imooc.muju_md.gson.NewsList;
import com.imooc.muju_md.gson.weather;
import com.imooc.muju_md.util.HttpUtil;
import com.imooc.muju_md.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final int  ITEM_MAIN= 1;
    private static final int  ITEM_COUNTY= 2;
    private static final int  ITEM_INTERNATION= 3;
    private static final int  ITEM_FUN= 4;
    private static final int  ITEM_NAOJING= 6;
    private static final int  ITEM_IT= 18;
    private static final int ITEM_FAVOTITE = 15;

    private String Country = "http://120.76.205.241:8000/news/qihoo?kw=%E5%9B%BD%E5%86%85&site=qq.com&apikey=T3THRDiUNhxrbYYOGXV8Onb6nMVm7aRKWKj9uyqHJSJH7tOfVGM4VnUkPHw9fA5Q";
    private String IT = "http://120.76.205.241:8000/post/leifeng?baid=132&apikey=T3THRDiUNhxrbYYOGXV8Onb6nMVm7aRKWKj9uyqHJSJH7tOfVGM4VnUkPHw9fA5Q";
    private String INTERNATION = "http://120.76.205.241:8000/news/qihoo?kw=%E6%AC%A7%E7%BE%8E&site=qq.com&apikey=T3THRDiUNhxrbYYOGXV8Onb6nMVm7aRKWKj9uyqHJSJH7tOfVGM4VnUkPHw9fA5Q";


    private List<Title> titleList = new ArrayList<Title>();
    private ListView listView;
    private TitleAdapter adapter;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout refreshLayout;

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;



    private Button mButton_weather;


    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);     //声明toolbar
        setSupportActionBar(toolbar);                               //用toolbar替换Actionbar
        final ActionBar actionBar = getSupportActionBar();          //声明Actionbar（因为Actionbar已经被替换，所以相当于声明toolbar）
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);              //显示左上角菜单栏，默认为false
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);     //设置菜单栏样式
        }
        actionBar.setDisplayShowTitleEnabled(true);                 //显示标题

        String RE = getIntent().getStringExtra("RE");

        int IS = getIntent().getIntExtra("IS",0);
        String X;
        if (IS == 0){
              X=  getweather("CN101190101");
        }
         else{
              X=   getweather(RE);
        }


        actionBar.setTitle("首页");                              //标题内容

   //     actionBar.setSubtitle(X);




        mButton_weather = (Button)findViewById(R.id.button_weather);
        mButton_weather.setText(X);
        mButton_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(MainActivity.this,weatherActivity.class);
                startActivity(i);
            }
        });

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_layout);   //声明SwipeRefreshLayout
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));  //设置下拉刷新转圈动画的颜色
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {     //设置下拉监听，需要重写onRefresh方法
            @Override
            public void onRefresh() {                               //重写onRefresh
                refreshLayout.setRefreshing(true);                  //表示正在刷新，可用isRfreshing()判断是否在刷新
                int itemName = parseString((String)actionBar.getTitle());   //获取当前actionbar的标题

                if(itemName == 15){
                    showfavorite();
                }
                else if (itemName == 2)
                    requestNNew(Country);
                else if (itemName == 3)
                    requestNNew(INTERNATION);
                else if (itemName == 18)
                    requestNNew(IT);
                else
                    requestNew(itemName);                                       //传入该标题，请求新闻
            }
        });

        final String idd = getIntent().getStringExtra("id");
        final int login = getIntent().getIntExtra("login",0);


        listView = (ListView)findViewById(R.id.list_view);
        adapter = new TitleAdapter(this,R.layout.list_view_item, titleList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = new Intent(MainActivity.this, ContentActivity.class);
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Title title = titleList.get(position);
                intent.putExtra("title",actionBar.getTitle());
                intent.putExtra("uri",title.getUri());
                intent.putExtra("dec",title.getDescr());
                intent.putExtra("pic",title.getImageUrl());
                intent.putExtra("id",idd);
                intent.putExtra("login",login);
                startActivity(intent);
            }
        });

        mButton1 = (Button)findViewById(R.id.button_main);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCurrentPage("首页",ITEM_MAIN);
            }
        });
        mButton2 = (Button)findViewById(R.id.button_inland);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCurrentPage("国内新闻",ITEM_COUNTY);
            }
        });
        mButton3 = (Button)findViewById(R.id.button_outland);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCurrentPage("国际新闻",ITEM_INTERNATION);
            }
        });
        mButton4 = (Button)findViewById(R.id.button_IT);
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCurrentPage("IT资讯",ITEM_IT);
            }
        });
        mButton5 = (Button)findViewById(R.id.button_favorite);
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCurrentPage("我的收藏",ITEM_FAVOTITE);
            }
        });




        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);    //声明带有侧滑功能的drawerlayout
        navigationView = (NavigationView)findViewById(R.id.nav_view);     //声明隐藏栏
        navigationView.setCheckedItem(R.id.nav_society);                  //设置初始选中条目
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() { //监听选择事件
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_society:
                        handleCurrentPage("首页",ITEM_MAIN);
                        break;
                    case R.id.nav_county:
                        handleCurrentPage("国内新闻",ITEM_COUNTY);
                        break;
                    case R.id.nav_internation:
                        handleCurrentPage("国际新闻",ITEM_INTERNATION);
                        break;
                    case R.id.nav_fun:
                        Intent z = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(z);
                        break;
                    case R.id.weather:
                        Intent i = new Intent(MainActivity.this,weatherActivity.class);
                        startActivity(i);
                        break;
                    case R.id.nav_it:
                        handleCurrentPage("IT资讯",ITEM_IT);
                        break;
                    case R.id.nav_favorite:
                        handleCurrentPage("我的收藏",ITEM_FAVOTITE);
                    default:
                        break;
                }
                drawerLayout.closeDrawers();    //关闭左侧栏
                return true;
            }
        });

        requestNew(ITEM_MAIN);

    }

    /**
     *  判断是否是当前页面,如果不是则请求处理数据
     */
    private void handleCurrentPage(String text, int item){
        ActionBar actionBar = getSupportActionBar();
        if (!text.equals(actionBar.getTitle().toString())){
            actionBar.setTitle(text);                                             //////////
   //         mTextView.setText(getweather());
            if(item == 15){
                showfavorite();
            }
            else if (item == 2)
                requestNNew(Country);
            else if (item == 3)
                requestNNew(INTERNATION);
            else if (item == 18)
                requestNNew(IT);
            else
                requestNew(item);                                       //传入该标题，请求新闻

            refreshLayout.setRefreshing(true);
        }
    }


    public String getweather(String x)  {

        final String st[] = new String[1];
        final String ZZZ = x;
                String Final="";
                GsonServer gs = new GsonServer();
                gs.getWeatherResponse(ZZZ,st);
                if(st[0].equals(null)){
                    gs.getWeatherResponse(ZZZ,st);
                };
                System.out.println(st[0]);
                weather w = Utility.parseJsonWithGsonW(st[0]);


                for(weather.HeWeather5Bean l: w.getHeWeather5() )
                {
                    Final+= l.getBasic().getCity();
                    Final+="   ";
                    Final+= l.getNow().getTmp()+"℃";
                    Final+="   ";
                    Final+= l.getNow().getCond().getTxt();
                    Final+="   ";
                    Final+="pm2.5:"+l.getAqi().getCity().getPm25();
                    for(weather.HeWeather5Bean.DailyForecastBean t:l.getDaily_forecast()){

                        Final+="   "+t.getTmp().getMin()+"~"+t.getTmp().getMax()+"℃";
                        break;
                    }
                }

                return Final;



    }

    public void showfavorite(){
        new Thread(){
            public void run(){
                JDBC_connection dbc = new JDBC_connection("NewsAPP");
                final String idd = getIntent().getStringExtra("id");
                System.out.println("id:"+idd);
                String Sql = "select * from News_Collection where Username = '" + idd + "';";
                NewsList N = dbc.getResultSet(Sql);
                if (N.newsList.isEmpty()){
                    System.out.println("999999999999999");
                }
                System.out.println("88888888888888");
                titleList.clear();
       for(News news : N.newsList)
        {
            System.out.println(news.title+news.picUrl+news.url);
            Title title = new Title(news.title,"",news.picUrl, news.url);
            titleList.add(title);
        }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();                            //adapter更新数据
                        listView.setSelection(0);                                  //定位到第一个新闻
                        Toast.makeText(getApplicationContext(),"刷新成功",Toast.LENGTH_SHORT);
                        refreshLayout.setRefreshing(false);                        //刷新结束
                    };
                });
            }
        }.start();

    }

    /**
     * 请求处理数据
     */
    public void requestNew(int itemName){

        // 根据返回到的 URL 链接进行申请和返回数据
        String address = response(itemName);    // response:获取标题名对应的key

        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "新闻加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();                 //返回的json数据
                final NewsList newlist = Utility.parseJsonWithGson(responseText);     //用Gson处理json数据，并传回NewsList实例中
                final int code = newlist.code;                                        //调用实例中的数据
                final String msg = newlist.msg;
                if (code == 200){
                    titleList.clear();
                    for (News news:newlist.newsList){                                 //将数据加载到Title上
                        Title title = new Title(news.title,news.description,news.picUrl, news.url);
                        titleList.add(title);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();                            //adapter更新数据
                            listView.setSelection(0);                                  //定位到第一个新闻
                            Toast.makeText(getApplicationContext(),"刷新成功",Toast.LENGTH_SHORT);
                            refreshLayout.setRefreshing(false);                        //刷新结束
                        };
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "数据错误返回",Toast.LENGTH_SHORT).show();
                            refreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });
    }

    public void requestNNew(String SSS){

        // 根据返回到的 URL 链接进行申请和返回数据
        final String address =  SSS ; // response:获取标题名对应的key

        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "新闻加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();                 //返回的json数据
                final NList nlist = Utility.parseJsonWithGsonN(responseText);     //用Gson处理json数据，并传回NewsList实例中
                final String code = nlist.getRetcode();                                        //调用实例中的数据

                if (code.equals("000000")){
                    titleList.clear();
                    String X = null;
                    for (NList.DataBean L:nlist.getData()){                                 //将数据加载到Title上
                        List<String> SS = L.getImageUrls();
                        if (SS==null)
                        {
                            if (address.equals(IT)){
                                X="http://pic.baike.soso.com/p/20130106/bki-20130106212330-8835793.jpg";
                            }
                            if (address.equals(Country)){
                                X="http://www.010xs.com/uploads/allimg/170228/11203G550_0.gif";
                            }
                            if (address.equals(INTERNATION)){
                                X=null;
                            }
                        }
                        else{
                            X=SS.get(SS.size()-1);
                        }
                        System.out.println("X=:"+X);
                        Title title = new Title(L.getTitle(),L.getPosterScreenName(),X,L.getUrl());
                        titleList.add(title);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();                            //adapter更新数据
                            listView.setSelection(0);                                  //定位到第一个新闻
                            Toast.makeText(getApplicationContext(),"刷新成功",Toast.LENGTH_SHORT);
                            refreshLayout.setRefreshing(false);                        //刷新结束
                        };
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "数据错误返回",Toast.LENGTH_SHORT).show();
                            refreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });
    }



    /**
     * 输入不同的类型选项，返回对应的 URL 链接
     */
    private String response(int itemName){
        String address = "http://47.94.86.217/get_data.json";
        switch(itemName){
            case ITEM_MAIN:
                break;
            case ITEM_COUNTY:
                address="http://120.76.205.241:8000/news/qihoo?kw=%E7%99%BD&site=qq.com&apikey=T3THRDiUNhxrbYYOGXV8Onb6nMVm7aRKWKj9uyqHJSJH7tOfVGM4VnUkPHw9fA5Q";
                break;
            case ITEM_INTERNATION:
                address="https://api.tianapi.com/world/?key=bf3ff557238de696fe0b7a35e98cd205&num=50";
                break;
            case ITEM_FUN:
                address="https://api.tianapi.com/huabian/?key=bf3ff557238de696fe0b7a35e98cd205&num=50";
                break;
            case ITEM_NAOJING:
                address="http://api.tianapi.com/txapi/naowan/?key=bf3ff557238de696fe0b7a35e98cd205";
                break;
            case ITEM_IT:

               address="https://api.tianapi.com/it/?key=bf3ff557238de696fe0b7a35e98cd205&num=50";
                break;
            default:
        }
        return address;
    }

    /**
     * 通过 actionbar.getTitle() 的参数，返回对应的 ItemName
     */
    private int parseString(String text){
        if (text.equals("首页")){
            return ITEM_MAIN;
        }
        if (text.equals("国内新闻")){
            return ITEM_COUNTY;
        }
        if (text.equals("国际新闻")){
            return ITEM_INTERNATION;
        }
        if (text.equals("娱乐新闻")){
            return ITEM_FUN;
        }
        if (text.equals("我的收藏")){
            return ITEM_FAVOTITE;
        }
        if (text.equals("IT资讯")){
            return ITEM_IT;
        }
        return ITEM_MAIN;
    }

    /**
     * 对侧边栏按钮进行处理，打开侧边栏
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    /**
     * 对返回键进行处理，如果侧边栏打开则关闭侧边栏，否则关闭 activity
     */
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawers();
        }else{
            finish();
        }
    }

    }


