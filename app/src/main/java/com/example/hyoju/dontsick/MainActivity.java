package com.example.hyoju.dontsick;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Button Login;
    private Button Join;
    private EditText pass;

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
    }
}
