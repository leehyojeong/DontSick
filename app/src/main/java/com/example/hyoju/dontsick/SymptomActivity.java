package com.example.hyoju.dontsick;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class SymptomActivity extends AppCompatActivity {

    FirebaseFirestore data = FirebaseFirestore.getInstance();

    EditText search;
    String mySearch;

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
                        data.collection(((MyClass)getApplication()).hos[index][i])//병명 검색
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for(DocumentSnapshot document : task.getResult()){
                                                String db = document.getData().toString();
                                                Log.d("증상",db);
                                                if(db.contains(mySearch)){//만약 그 증상이 있으면
                                                    LinearLayout layout = new LinearLayout(SymptomActivity.this);
                                                    TextView text = new TextView(SymptomActivity.this);
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
