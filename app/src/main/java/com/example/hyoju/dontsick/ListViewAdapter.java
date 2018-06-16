package com.example.hyoju.dontsick;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

public class ListViewAdapter extends BaseAdapter {

    ArrayList<Item> listItem = new ArrayList<Item>();
    viewHolder viewholder;
    class viewHolder{
        TextView symptom_name;
        TextView symptom_info;
        TextView symptom_mean;
        String hospital_name;
        Button searchButton;
    }
    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


            int pos = position;
            final Context context = parent.getContext();

            if(convertView == null){

                convertView = LayoutInflater.from(context).inflate(R.layout.list_item,null);

                viewholder = new viewHolder();

                viewholder.symptom_name = (TextView)convertView.findViewById(R.id.symptomName);
                viewholder.symptom_info = (TextView)convertView.findViewById(R.id.symptomInfo);
                viewholder.symptom_mean = (TextView)convertView.findViewById(R.id.symptommean);
                viewholder.searchButton = (Button)convertView.findViewById(R.id.hospitalButton);
                convertView.setTag(viewholder);
            }else{
                viewholder = (viewHolder)convertView.getTag();
            }




            Item my = listItem.get(position);
             viewholder.hospital_name =listItem.get(position).hospital;
                viewholder.symptom_name.setText(listItem.get(position).getName());
            viewholder.symptom_info.setText(listItem.get(position).getSym());
            viewholder.symptom_mean.setText(listItem.get(position).getMean());
            viewholder.searchButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v.getId() == R.id.hospitalButton){
                        MapActivity m = new MapActivity();
                        switch(listItem.get(position).hospital){//여기다가 지도 연결하셈
                            case "내과":
                                m.hospital="내과";
                                break;
                            case "외과":
                                m.hospital="외과";
                                break;
                            case "안과":
                                m.hospital="안과";
                                break;
                            case "치과":
                                m.hospital="치과";
                                break;
                            case "이비인후과":
                                m.hospital="이비인후과";
                                break;
                            case "피부과":
                                m.hospital="피부과";
                                break;
                            case "소아청소년과":
                                m.hospital="소아청소년과";
                                break;
                        }
                        Intent intent = new Intent(context, MapActivity.class);
                        context.startActivity(intent);
                    }

                }
            });
        return convertView;
    }

    public void addItem(String name, String symp, String mean,String hospital){
        Item item= new Item(name,symp,mean,hospital);

        listItem.add(item);
    }
}
