package com.example.hyoju.dontsick;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    LinearLayout layout;
    TextView name;
    TextView symptom;
    TextView mean;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_disease);


    }
    public void Search(){
        name = (TextView)findViewById(R.id.Name);
        symptom = (TextView)findViewById(R.id.symptom);
        mean = (TextView)findViewById(R.id.mean);

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
                                        name.setText(my);
                                        if(list.equals(my)){
                                                String list_tmp = entry.getValue().toString();//getValue가 증상
                                                list_tmp =   list_tmp.replace("[","");
                                                list_tmp =  list_tmp.replace("]","");
                                                    String sym[] = list_tmp.split(",");
                                                    for(int i = 0;i<sym.length;i++) {
                                                        sym[i] = sym[i].trim();
                                                    }
                                                String symptomTxt = "";
                                                for(int i = 0;i<sym.length-1;i++){
                                                    symptomTxt += ("#"+sym[i]);
                                                }//증상출력
                                                symptom.setText(symptomTxt);
                                                String meaning = sym[sym.length-1];
                                                mean.setText(meaning);
                                                 inDisease.add(m.allHospital[finalI]);
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
        InsertButton();
    }
    void InsertButton(){
        layout = (LinearLayout)findViewById(R.id.MyLayout);

        for(int i = 0;i<inDisease.size();i++){
            Button btn = new Button(SearchDiseaseActivity.this);

        }
    }
}
