package com.example.hyoju.dontsick;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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

import static com.example.hyoju.dontsick.R.layout.activity_symptom;

public class SymptomActivity extends AppCompatActivity {

    FirebaseFirestore data = FirebaseFirestore.getInstance();

    EditText search;
    String mySearch;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_symptom);

        final View view = (View)getLayoutInflater().inflate(R.layout.activity_symptom,null);
        final LinearLayout hospitalP = (LinearLayout)view.findViewById(R.id.hospital_page);
        final LinearLayout diseaseP = (LinearLayout)view.findViewById(R.id.disease_page);




                search = (EditText)findViewById(R.id.symptom_search);
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    mySearch = search.getText().toString();//증상을 입력
                    mySearch = mySearch.trim();
                    int index = ((MyClass)getApplication()).hosIndex;
                   for(int i = 0;i< ((MyClass)getApplication()).hos[index].length;i++) {//병원
                       String hospital = ((MyClass) getApplication()).hos[index][i];
                       String hospitalArr[] = hospital.split(",");
                       for (int j = 0; j < hospitalArr.length; j++) {
                           hospitalArr[j] = hospitalArr[j].trim();
                       }
                       for (int j = 0; j < hospitalArr.length; j++)
                           data.collection(hospitalArr[j])//병명 검색
                                   .get()
                                   .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                       @Override
                                       public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                           if (task.isSuccessful()) {
                                               for (DocumentSnapshot document : task.getResult()) {
                                                   Map<String, Object> map = document.getData();
                                                   for (Map.Entry<String, Object> entry : map.entrySet()) {

                                                       String list = entry.getValue().toString();//getValue가 증상
                                                       if (list.contains(mySearch)) {

                                                           FrameLayout f = (FrameLayout)findViewById(R.id.frame);
                                                          //  f.removeViewAt(0);
                                                           TextView name = new TextView(SymptomActivity.this);
                                                           name.setText(entry.getKey().toString());
                                                           name.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                                           diseaseP.addView(name);
                                                           Log.d("CHECK", entry.getKey());//getKey가 병이름
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
    }
    public void lookHospital(){

    }
    public void lookDisease() {

    }

}
