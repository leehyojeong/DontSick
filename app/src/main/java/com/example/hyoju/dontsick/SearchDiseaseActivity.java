package com.example.hyoju.dontsick;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

    FirebaseFirestore data = FirebaseFirestore.getInstance();

    final ArrayList<String> inDisease = new ArrayList<String>();

    String want_search="";
    LinearLayout layout;
    TextView name;
    TextView symptom;
    TextView mean;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_disease);

        Log.d("들어옴","들어옴");

        Intent intent= getIntent();
       want_search =  intent.getExtras().getString("EditTextValue");

        name = (TextView)findViewById(R.id.Name);
        symptom = (TextView)findViewById(R.id.symptom);
        mean = (TextView)findViewById(R.id.mean);

        Log.d("값 확인", want_search);

        layout = (LinearLayout)findViewById(R.id.MyLayout);

        name.setText(want_search);

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
                                        String my = want_search.trim();//내가 입력한 값
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

                                                Button btn = new Button(SearchDiseaseActivity.this);
                                                btn.setBackgroundColor(Color.rgb(255,255,255));
                                                btn.setTextColor(Color.rgb( 248 ,30 ,54));
                                               btn.setWidth(10);
                                               btn.setHeight(15);
                                               btn.setText(m.allHospital[finalI]);
                                               btn.setId(R.id.hospitalButton);
                                                layout.addView(btn);

                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }
    public void Search(String string){
        want_search = string;

    }
    void InsertButton(){

    }
}
