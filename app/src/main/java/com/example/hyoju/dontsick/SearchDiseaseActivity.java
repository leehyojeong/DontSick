package com.example.hyoju.dontsick;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class SearchDiseaseActivity extends AppCompatActivity {
    public String myEdit;
    FirebaseFirestore data = FirebaseFirestore.getInstance();
    final ArrayList<String> inDisease = new ArrayList<String>();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_disease);


    }
    public void Search(){
        final MyClass m = new MyClass();
        for(int i = 0;i<m.allHospital.length;i++){
            final int finalI = i;
            data.collection(m.allHospital[i])
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot document: task.getResult()){

                                    Map<String,Object> map = document.getData();

                                    for(Map.Entry<String,Object> entry: map.entrySet()){
                                        String list = entry.getKey().toString().trim();
                                        String my = myEdit;//내가 입력한 값
                                        if(list.equals(my)){
                                            Log.d("key",list);
                                            inDisease.add(m.allHospital[finalI]);
                                            Log.d("병원",m.allHospital[finalI]);
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }
}
