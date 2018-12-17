package com.example.minwoo.seoul18app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity{
final Context context=this;
ListView bookList;

private Button addButton, editButton, deleteButton;

ArrayList<String> nameList=new ArrayList<String>();
    ArrayList<String> phoneList=new ArrayList<String>();
    String[] tempA={""};
    CustomAdapter customAdapter=null;
    CustomAdapter2 customAdapter2=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        bookList=(ListView)findViewById(R.id.optionListView);
        addButton=(Button)findViewById(R.id.addB);
        addButton.setOnClickListener(new Clicker1());
        TinyDB tinydb=new TinyDB(context);
        nameList=tinydb.getListString("nameLists");
        phoneList=tinydb.getListString("phoneLists");
        if(nameList.size()>0){
        customAdapter2 = new CustomAdapter2(getApplicationContext(), nameList.toArray(new String[nameList.size()])
                , phoneList.toArray(new String[phoneList.size()]));
            bookList.setAdapter(customAdapter2);
        }

      /*          else {
            customAdapter = new CustomAdapter(getApplicationContext(), tempA, tempA);
        }
        bookList.setAdapter(customAdapter);*/
    }
/*
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        TinyDB tinydb=new TinyDB(context);
        String names = tinydb.getString("myname");
        String phones = tinydb.getString("myphone");
        // String phones = data.getStringExtra("phones");
        customAdapter2 = new CustomAdapter2(getApplicationContext(), nameList.toArray(new String[nameList.size()])
                , phoneList.toArray(new String[phoneList.size()]));
        bookList.setAdapter(customAdapter2);
        tinydb.putListString("phoneLists", phoneList);
    }
*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102) {
            if(resultCode == Activity.RESULT_OK){
                TinyDB tinydb=new TinyDB(context);

              //  String phones = tinydb.getString("myphone");
             //   String names = tinydb.getString("myname");
//nameList=tinydb.getListString("an");
//phoneList=tinydb.getListString("pn");
               String phones = data.getStringExtra("phones");
                String names=data.getStringExtra("names");

                if(names!=null&&phones!=null){
                    //  books.add(new Book(names,phones,emails));

                    phoneList.add(phones);
                    nameList.add(names);
                }

                customAdapter2 = new CustomAdapter2(getApplicationContext(), nameList.toArray(new String[nameList.size()])
                        , phoneList.toArray(new String[phoneList.size()]));
                bookList.setAdapter(customAdapter2);

                tinydb.putListString("phoneLists", phoneList);
                tinydb.putListString("nameLists", nameList);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResu
    private class Clicker1 implements View.OnClickListener {
        public void onClick(View v) {
            Bundle myData;
            switch (v.getId()) {
                case R.id.addB:
                    Intent addIntent = new Intent(context, AddActivity.class);
                    startActivityForResult(addIntent, 102);
                    break;
            }
        }
    }
}
