package com.example.hyoju.dontsick;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private Button head;
    private Button nextBtn;
    private Button Arm1,Arm2,Leg,Chest,Stomach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final List<String> selectItems=new ArrayList<String>();

        Arm1 = (Button)findViewById(R.id.arm1);
        Arm1.setOnClickListener((View.OnClickListener) this);
        Arm2 = (Button)findViewById(R.id.arm2);
        Arm2.setOnClickListener((View.OnClickListener) this);
        Leg = (Button)findViewById(R.id.leg);
        Leg.setOnClickListener((View.OnClickListener) this);
        Chest = (Button)findViewById(R.id.chest);
        Chest.setOnClickListener((View.OnClickListener) this);
        Stomach = (Button)findViewById(R.id.stomach);
        Stomach.setOnClickListener((View.OnClickListener) this);

        head=(Button) findViewById(R.id.head);
        head.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final String[] items=new String[]{"눈","귀","코","입","이","목"};
                AlertDialog.Builder dialog=new AlertDialog.Builder(SearchActivity.this);
                dialog.setTitle("부위를 선택하세요")
                        .setMultiChoiceItems(items,
                                new boolean[]{false,false,false,false,false,false},
                                new DialogInterface.OnMultiChoiceClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                                        if(isChecked){
                                            Toast.makeText(SearchActivity.this,items[which],Toast.LENGTH_SHORT).show();
                                            selectItems.add(items[which]);}
                                        else{
                                            selectItems.remove(items[which]);
                                        }
                                    }
                                })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                if(selectItems.size()==0){
                                    Toast.makeText(SearchActivity.this,"선택되지 않았습니다",Toast.LENGTH_SHORT).show();
                                }else{
                                    String items="";
                                    for(String selectedItmes:selectItems){
                                        items+=(selectedItmes+", ");
                                    }
                                    selectItems.clear();

                                    items=items.substring(0,items.length()-2);
                                    Toast.makeText(SearchActivity.this,items,Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).create().show();
            }
        });
    }

    public void onClick(View V) {
       
    }
}
