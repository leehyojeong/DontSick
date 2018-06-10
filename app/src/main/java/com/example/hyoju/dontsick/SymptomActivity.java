package com.example.hyoju.dontsick;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class SymptomActivity extends AppCompatActivity{

    FirebaseFirestore data = FirebaseFirestore.getInstance();

    EditText search;
    String mySearch;
    private Button Map;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom);


        search = (EditText)findViewById(R.id.symptom_search);
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    mySearch = search.getText().toString();//증상을 입력
                    mySearch = mySearch.trim();
                    int index = ((MyClass)getApplication()).hosIndex;
                    for(int i = 0;i< ((MyClass)getApplication()).hos[index].length;i++){//병원
                        String hospital = ((MyClass)getApplication()).hos[index][i];
                        String hospitalArr[] = hospital.split(",");
                        for(int j = 0;j<hospitalArr.length;j++){
                            hospitalArr[i] = hospitalArr[i].trim();
                        }
                        for(int j = 0;j<hospitalArr.length;j++)
                        Log.d("CHECK1",hospital);
                        data.collection(hospitalArr[i])//병명 검색
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for(DocumentSnapshot document : task.getResult()){
                                                Map<String, Object> map = document.getData();
                                                for(Map.Entry<String,Object> entry:map.entrySet()){
                                                    Log.d("CHECK",entry.getKey());//getKey가 병이름
                                                    String list =  entry.getValue().toString();//getValue가 증상
                                                    if(list.contains(mySearch)){

                                                    }
                                                }
                                            }
                                        }
                                    }
                                });
                    }
                    return true;
                }
                return false;
            }
        });

        Map = (Button) findViewById(R.id.map);
        Map.setOnClickListener(new Button.OnClickListener() {//회원가입버튼 클릭할 때

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });
    }
    public void lookHospital(){

    }
    public void lookDisease() {

    }

}