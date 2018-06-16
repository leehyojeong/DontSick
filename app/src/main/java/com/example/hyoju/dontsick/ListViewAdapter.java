package com.example.hyoju.dontsick;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    ArrayList<Item> listItem = new ArrayList<Item>();
    viewHolder viewholder;
    class viewHolder{
        TextView symptom_name;
        TextView symptom_info;
        TextView symptom_mean;
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
    public View getView(int position, View convertView, ViewGroup parent) {


            int pos = position;
            Context context = parent.getContext();

            if(convertView == null){

                convertView = LayoutInflater.from(context).inflate(R.layout.list_item,null);

                viewholder = new viewHolder();
                viewholder.symptom_name = (TextView)convertView.findViewById(R.id.symptomName);
                viewholder.symptom_info = (TextView)convertView.findViewById(R.id.symptomInfo);
                viewholder.symptom_mean = (TextView)convertView.findViewById(R.id.symptommean);

                convertView.setTag(viewholder);
            }else{
                viewholder = (viewHolder)convertView.getTag();
            }




            Item my = listItem.get(position);

           viewholder.symptom_name.setText(listItem.get(position).getName());
            viewholder.symptom_info.setText(listItem.get(position).getSym());
            viewholder.symptom_mean.setText(listItem.get(position).getMean());

        return convertView;
    }

    public void addItem(String name, String symp, String mean){
        Item item= new Item();

        item.setMean(mean);
        item.setName(name);
        item.setSym(symp);

        listItem.add(item);
        Log.d("들어옴","들어옴");
    }
}
