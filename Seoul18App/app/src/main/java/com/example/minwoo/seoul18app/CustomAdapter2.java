package com.example.minwoo.seoul18app;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Minwoo on 2016-10-18.
 */
public class CustomAdapter2 extends BaseAdapter{
    final Context context;
    String langList[];
    int langs[];
    String nameList[];
    String phoneList[];
    LayoutInflater inflter;

    public CustomAdapter2(Context context, String[] nameList, String[] phoneList) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.option_list2, null);
        TextView n = (TextView) view.findViewById(R.id.textView);
        TextView p = (TextView) view.findViewById(R.id.textView2);
        Button deleteButton=(Button)view.findViewById(R.id.deleteB2);
        deleteButton.setTag(i);
        n.setText(nameList[i]);
        p.setText(phoneList[i]);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                int positionToRemove = (int)v.getTag();
                removeItem(positionToRemove);
                notifyDataSetChanged();
            }
        });

        return view;
    }
    public void removeItem(int position){
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
        TinyDB tinydb=new TinyDB(context);
        tinydb.putListString("phoneLists", a);
        tinydb.putListString("nameLists", b);
        notifyDataSetChanged(); //refresh your listview based on new data

    }
}
