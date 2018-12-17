package com.example.minwoo.seoul18app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
Button submitButton;
EditText editText1,editText2;
String names,phones,emails;
    ArrayList<String> nameList=new ArrayList<String>();
    ArrayList<String> phoneList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        editText1=(EditText)findViewById(R.id.namelayoutButton);
        editText2=(EditText)findViewById(R.id.phonelayoutButton);
        submitButton=(Button)findViewById(R.id.addButton2);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       names=editText1.getText().toString();
       phones=editText2.getText().toString();
  //    Intent intent = new Intent(getBaseContext(), AddressActivity.class);
    //    Intent intent=getIntent();
        Intent intent=new Intent();
       intent.putExtra("names", names);
        intent.putExtra("phones",phones);


        TinyDB tinydb=new TinyDB(context);
      /*  nameList.add(names);
        phoneList.add(phones);
        tinydb.putListString("an",nameList);
        tinydb.putListString("pn",phoneList);
       tinydb.putString("myphone", phones);
        tinydb.putString("myname", names);
        */

/*
        startActivityForResult(intent,102);
        setResult(Activity.RESULT_OK,intent);*/
    //    finish();

        setResult(RESULT_OK,intent);
        finish();


    }
}
