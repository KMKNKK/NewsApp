package com.imooc.muju_md;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.imooc.muju_md.message.ReadFile;
import com.imooc.muju_md.message.cityList;
import com.imooc.muju_md.util.Utility;


import com.imooc.muju_md.message.city;


public class weatherActivity extends AppCompatActivity {


    private Button mButton;
    private EditText mEditText;
    private String RE;
    int ii = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);



        mButton = (Button)findViewById(R.id.button) ;
        mEditText = (EditText)findViewById(R.id.editText2) ;

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = mEditText.getText().toString();

                Context context = getApplicationContext();
                cityList c = null;
                try {
                    c = Utility.parseJsonWithGsonweather(ReadFile.Read(context));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for(city city : c.cityList ){
                    if(city.getCity_name().equals(x))
                    {
                        ii =1;
                        RE = city.getId();
                        System.out.println("dasdasdasdassssssssssssssssssssssssssssss"+RE);
                        Intent i = new Intent(weatherActivity.this,MainActivity.class);
                        i.putExtra("RE",RE);
                        i.putExtra("IS",1);
                        startActivity(i);
                    }
                }
                if(ii==0)
                Toast.makeText(context, "输入城市名有误", Toast.LENGTH_SHORT).show();


            }
        });


    }
}
