package com.example.hyoju.dontsick;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class JoinActivity extends AppCompatActivity {

   private Button join;
  //  FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        join = (Button) findViewById(R.id.join);
        join.setOnClickListener(new Button.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }
        );
    }
}
