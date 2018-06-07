package com.example.hyoju.dontsick;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements OnClickListener{

    private Button head;
    private Button nextBtn;
    public static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final List<String> selectItems=new ArrayList<String>();
        final String[] selectItem = {null};
        mContext = this;

        head=(Button) findViewById(R.id.head);
        head.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final String[] items=new String[]{"눈","귀","코","입","이","목"};
                AlertDialog.Builder dialog=new AlertDialog.Builder(SearchActivity.this);
                dialog.setTitle("부위를 선택하세요")
                        .setSingleChoiceItems(items, -1,
                                new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        selectItem[0] = items[which];
                                        Toast.makeText(SearchActivity.this,items[which],Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                if(selectItem[0] == null){
                                    Toast.makeText(SearchActivity.this,"선택되지 않았습니다",Toast.LENGTH_SHORT).show();
                                }else{
                                    ((MyClass)getApplication()).part = selectItem[0];
                                    switch(selectItem[0]){
                                        case "눈":
                                            ((MyClass)getApplication()).hosIndex =0;
                                            break;
                                        case "코":
                                            ((MyClass)getApplication()).hosIndex =1;
                                            break;
                                        case "입":
                                            ((MyClass)getApplication()).hosIndex =2;
                                            break;
                                        case "귀":
                                            ((MyClass)getApplication()).hosIndex =3;
                                            break;
                                        case "이":
                                            ((MyClass)getApplication()).hosIndex =4;
                                            break;
                                        case "목":
                                            ((MyClass)getApplication()).hosIndex =5;
                                            break;
                                    }
                                    Intent intent = new Intent(getApplicationContext(), SymptomActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }).create().show();
            }
        });

    }
    public void onClick(View view) {
        if(view.getId() == R.id.arm1 || view.getId() == R.id.arm2 )
        {
            ((MyClass)getApplication()).part = "팔";
            ((MyClass)getApplication()).hosIndex =6;
            Intent intent = new Intent(getApplicationContext(), SymptomActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.leg ) {
            ((MyClass)getApplication()).part = "다리";
            ((MyClass)getApplication()).hosIndex =7;
            Intent intent = new Intent(getApplicationContext(), SymptomActivity.class);
            startActivity(intent);
        }
        else if(view.getId() ==R.id.chest){
            ((MyClass)getApplication()).part = "가슴";
            ((MyClass)getApplication()).hosIndex =8;
            Intent intent = new Intent(getApplicationContext(), SymptomActivity.class);
            startActivity(intent);
        }else {
            ((MyClass)getApplication()).part = "배";
            ((MyClass)getApplication()).hosIndex =9;
            Intent intent = new Intent(getApplicationContext(), SymptomActivity.class);
            startActivity(intent);
          }
        }
}
