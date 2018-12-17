package com.example.minwoo.seoul18app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomAdapter extends BaseAdapter{
    Context context;
    String nameList[];
    String phoneList[];
    LayoutInflater inflter;

    public CustomAdapter(Context context, String[] nameList, String[] phoneList) {
        this.context = context;
        this.nameList = nameList;
        this.phoneList = phoneList;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return nameList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View myView, ViewGroup viewGroup) {
        myView = inflter.inflate(R.layout.option_list, null);
        TextView names = (TextView) myView.findViewById(R.id.ttt1);
        TextView phones = (TextView) myView.findViewById(R.id.ttt2);
        Button editButton=(Button)myView.findViewById(R.id.editB);
        Button deleteButton=(Button)myView.findViewById(R.id.deleteB);
      //  deleteButton.setTag(i);
    //    editButton.setTag(i);
    //    names.setText(nameList[i]);
    //    phones.setText(phoneList[i]);
        names.setText("shit");
            phones.setText("Shit");
      /*  deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                int positionToRemove = (int)v.getTag();
                removeItem(positionToRemove);
                notifyDataSetChanged();
            }
        });*/
        return myView;
    }
   /* public void removeItem(int position){
        //convert array to ArrayList, delete item and convert back to array
        ArrayList<String> a = new ArrayList<>(Arrays.asList(nameList));
        a.remove(position);
        String[] s = new String[a.size()];
        s=a.toArray(s);
        nameList = s;
        ArrayList<String> b = new ArrayList<>(Arrays.asList(phoneList));
        b.remove(position);
        String[] ps= new String[b.size()];
        ps=b.toArray(ps);
        phoneList = ps;
        notifyDataSetChanged(); //refresh your listview based on new data

    }*/
}
