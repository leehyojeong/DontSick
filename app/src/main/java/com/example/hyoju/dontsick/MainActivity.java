package com.example.hyoju.dontsick;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {


    private Button Login;
    private Button Join;
    private Button NoJoin;
    private EditText pass;
    private TextView login;

    //Map<String, Object> user = new HashMap<>();


    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pass = (EditText) findViewById(R.id.password);
        Login = (Button) findViewById(R.id.login);

        Login.setOnClickListener(new Button.OnClickListener() {//로그인버튼 클릭할 때
            @Override
            public void onClick(View v) {

                database.collection("user")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        String text = String.valueOf(document.getData());
                                        Log.d("text",text);
                                        String Info[] = text.split(",");//데이터베이스에서 데이터 받아옴
                                        String passS[] = Info[2].split("=");//휴대폰번호 text와 값 분리
                                        String cPass = pass.getText().toString().trim();//내가 입력받은 거에서 공백 제거
                                        passS[1] = passS[1].replace("}","");//}를 공백으로 대체
                                        Log.d("user",cPass);
                                        Log.d("database",passS[1]);
                                        String info = passS[1].trim();
                                        if (info.equals(cPass)) {
                                            ((MyClass)getApplication()).userInfo = text;
                                            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                    pass.setText("");
                                } else {
                                    Log.w("TAG", "Error Gettting document.", task.getException());
                                }
                            }

                        });
            }

        });

        Join = (Button) findViewById(R.id.join);
        Join.setOnClickListener(new Button.OnClickListener() {//회원가입버튼 클릭할 때

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        NoJoin = (Button)findViewById(R.id.noData);
        NoJoin.setOnClickListener(new Button.OnClickListener(){//로그인 없이 이용하기

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }

        });
    }
}
