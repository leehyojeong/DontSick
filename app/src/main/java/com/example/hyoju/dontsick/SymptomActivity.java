package com.example.hyoju.dontsick;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.Map;

//<<<<<<< HEAD
import static com.example.hyoju.dontsick.R.layout.activity_symptom;


    public class SymptomActivity extends AppCompatActivity {


        FirebaseFirestore data = FirebaseFirestore.getInstance();

        EditText search;
        String mySearch;
        private Button Map;
        

        @SuppressLint("WrongViewCast")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(activity_symptom);

            final LinearLayout layout = (LinearLayout)findViewById(R.id.lay);
            final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.LEFT;

            //layout.setVisibility(View.GONE);
            search = (EditText) findViewById(R.id.symptom_search);
            search.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {


                    if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                        layout.removeAllViews();

                        mySearch = search.getText().toString().trim();//증상을 입력
                        int index = ((MyClass) getApplication()).hosIndex;
                        final String hospitalArr[] =  new String[((MyClass) getApplication()).hos[index].length];
                        for (int i = 0; i < ((MyClass) getApplication()).hos[index].length; i++) {//병원
                            String hospital = ((MyClass) getApplication()).hos[index][i];
                            Log.d("hospital",hospital);
                            hospitalArr[i] = hospital.trim();
                            Log.d("hospitalArr",hospitalArr[i]);
                        }

                        for (int j = 0; j < hospitalArr.length; j++) {
                            final int finalJ = j;

                            final LinearLayout tmp = new LinearLayout(SymptomActivity.this);
                            tmp.setOrientation(LinearLayout.VERTICAL);

                            TextView text = new TextView(SymptomActivity.this);
                            text.setText(" ");
                            tmp.addView(text);

                            LinearLayout mapL = new LinearLayout(SymptomActivity.this);
                            mapL.setOrientation(LinearLayout.HORIZONTAL);

                            TextView key = new TextView(SymptomActivity.this);
                           key.setText(hospitalArr[finalJ]);
                            key.setId(R.id.hospital);
                            key.setTextSize(50);
                            key.setGravity(Gravity.LEFT);
                            mapL.addView(key);//병원 출력

                            Button btn = new Button(SymptomActivity.this);
                           ViewGroup.LayoutParams btnParams = new ViewGroup.LayoutParams(70,100);
                           btn.setLayoutParams(btnParams);
                            btn.setBackground(ContextCompat.getDrawable(SymptomActivity.this,R.drawable.map));
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                                    startActivity(intent);
                                }
                            });
                            mapL.addView(btn);

                            tmp.addView(mapL);
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
                                                            Log.d("string",list);
                                                          list =   list.replace("[","");
                                                           list =  list.replace("]","");
                                                            String sym[] = list.split(",");
                                                            for(int i = 0;i<sym.length;i++){
                                                                sym[i] = sym[i].trim();
                                                            }

                                                           TextView key = new TextView(SymptomActivity.this);//이름
                                                            key.setText(entry.getKey());
                                                            key.setId(R.id.diseaseName);
                                                            key.setTextSize(25);
                                                            key.setGravity(Gravity.LEFT);//병이름 출력

                                                            LinearLayout sympL = new LinearLayout(SymptomActivity.this);
                                                            HorizontalScrollView sc = new HorizontalScrollView(SymptomActivity.this);
                                                            sympL.setOrientation(LinearLayout.HORIZONTAL);
                                                            for(int i = 0;i<sym.length-1;i++){
                                                                TextView symp = new TextView(SymptomActivity.this);//증상
                                                                symp.setLines(1);
                                                                symp.setText("#"+sym[i]);
                                                                symp.setTextSize(18);
                                                                symp.setGravity(Gravity.LEFT);
                                                               sympL.addView(symp);
                                                            }//증상출력
                                                            sc.addView(sympL);
                                                            tmp.addView(key);
                                                            tmp.addView(sc);
                                                            TextView n = new TextView(SymptomActivity.this);
                                                            n.setText(sym[sym.length-1]+"\n");//병 의미 출력
                                                            tmp.addView(n);


                                                            Log.d("hospital", hospitalArr[finalJ]);
                                                            Log.d("CHECK", entry.getKey());//getKey가 병이름
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    });


                            layout.addView(tmp);
                        }//for끝
                    }
                    return true;
                }
                //key끝
            });
        }

        public void lookHospital() {

        }

        public void lookDisease() {

        }

    }
