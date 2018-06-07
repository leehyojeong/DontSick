package com.example.hyoju.dontsick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class SymptomActivity extends AppCompatActivity {

    FirebaseFirestore data = FirebaseFirestore.getInstance();

    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom);

        search = (EditText)findViewById(R.id.symptom_search);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });
    }
    public void lookHospital(){

    }
    public void lookDisease() {

    }

}
