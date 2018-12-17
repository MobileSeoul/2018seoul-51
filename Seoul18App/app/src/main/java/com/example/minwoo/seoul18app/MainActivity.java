package com.example.minwoo.seoul18app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button startButton, addressButton, detectButton, button4, button5, button6;
    final Context context=this;
    ArrayList<String > phones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestSmsPermission();
        startButton=(Button)findViewById(R.id.button1);
        addressButton=(Button)findViewById(R.id.button2);
        detectButton=(Button)findViewById(R.id.button3);
        startButton.setOnClickListener(new Clicker1());
        addressButton.setOnClickListener(new Clicker1());
        detectButton.setOnClickListener(new Clicker1());
       // TinyDB tinyDB=new TinyDB(context);
        //ArrayList<String> phones=tinyDB.getListString("phoneLists");
        //Toast.makeText(this,phones.get(0),Toast.LENGTH_LONG).show();
    }
    private void requestSmsPermission() {
        String permission = Manifest.permission.SEND_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }
    private class Clicker1 implements View.OnClickListener {
        public void onClick(View v) {
            Bundle myData;
            switch (v.getId()) {
                case R.id.button1:
                    TinyDB tinyDB=new TinyDB(context);
                    phones=tinyDB.getListString("phoneLists");
                    if(phones.size()>0) {
                        Intent safeIntent = new Intent(context, SafeActivity.class);
                        myData = new Bundle();
                        myData.putInt("myRequestCode", 101);
                        startActivityForResult(safeIntent, 101);
                    }else{
                        Toast.makeText(context,"연락처 관리에서 연락처를 최소 1개 이상 등록 후 사용해주세요",Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.button2:
                    Intent addressIntent = new Intent(context, AddressActivity.class);
                    myData = new Bundle();
                    myData.putInt("myRequestCode", 102);
                    startActivityForResult(addressIntent, 102);
                    break;
                case R.id.button3:
                    Intent detectIntent = new Intent(context, DetectActivity.class);
                    myData = new Bundle();
                    myData.putInt("myRequestCode", 103);
                    startActivityForResult(detectIntent, 103);

                    break;
            }
        }
    }
}
