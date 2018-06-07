package com.example.hyoju.dontsick;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {

   private Button join;
   EditText name;
   EditText password;
    String sname;
    String spass;

    Map<String, Object> user = new HashMap<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        name = (EditText)findViewById(R.id.editName);
        password = (EditText)findViewById(R.id.editPass);



        join = (Button) findViewById(R.id.join);
        join.setOnClickListener(new Button.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sname = name.getText().toString();
                                        spass = password.getText().toString();
                                        user.put("휴대폰번호",spass);
                                        user.put("이름",sname);
                                        user.put("즐겨찾기","");
                                        db.collection("user")
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (DocumentSnapshot document : task.getResult()) {
                                                                String text = String.valueOf(document.getData());
                                                                Log.d("text",text);
                                                                String Info[] = text.split(",");//데이터베이스 데이터 분리
                                                                String passS[] = Info[2].split("=");//비밀번호만 분리
                                                                passS[1] = passS[1].replace("}","");//{를 공백으로 대체
                                                                String info = passS[1].trim();//비밀번호 공백 제거
                                                                spass = spass.trim();//혹시 모를 공백 제거(입력받은 값)
                                                                if(spass.equals(info)){
                                                                    name.setText("");
                                                                    password.setText("");
                                                                    user.clear();
                                                                    return;
                                                                }
                                                                db.collection("user")
                                                                        .add(user)
                                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                            @Override
                                                                            public void onSuccess(DocumentReference documentReference) {
                                                                                user.clear();
                                                                                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                                                                                startActivity(intent);
                                                                            }
                                                                        })
                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Log.w("Failure","fail");
                                                                            }
                                                                        });
                                                            }
                                                        } else {
                                                            Log.w("TAG", "Error Gettting document.", task.getException());
                                                        }
                                                    }
                                                });
                                    }
                                }
        );
    }
}
