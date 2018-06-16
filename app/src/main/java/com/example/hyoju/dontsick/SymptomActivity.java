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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

import static com.example.hyoju.dontsick.R.layout.activity_symptom;


    public class SymptomActivity extends AppCompatActivity {


        FirebaseFirestore data = FirebaseFirestore.getInstance();

        EditText search;
        String mySearch;
        private Button Map;
        ListViewAdapter adapter;

       ListView listview;


        @SuppressLint("WrongViewCast")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(activity_symptom);

            adapter = new ListViewAdapter();


            listview=(ListView)findViewById(R.id.myListView);
           listview.setAdapter(adapter);


            search = (EditText) findViewById(R.id.symptom_search);
            search.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                      //  listview.removeAllViews();

                        mySearch = search.getText().toString().trim();//증상을 입력
                        int index = ((MyClass) getApplication()).hosIndex;
                        final String hospitalArr[] =  new String[((MyClass) getApplication()).hos[index].length];
                        for (int i = 0; i < ((MyClass) getApplication()).hos[index].length; i++) {//병원
                            String hospital = ((MyClass) getApplication()).hos[index][i];
                            hospitalArr[i] = hospital.trim();
                        }
                        for (int j = 0; j < hospitalArr.length; j++) {
                            final int finalJ = j;

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
                                                          list =   list.replace("[","");
                                                           list =  list.replace("]","");
                                                            String sym[] = list.split(",");
                                                            for(int i = 0;i<sym.length;i++){
                                                                sym[i] = sym[i].trim();
                                                            }

                                                            String disName = entry.getKey();
                                                            String symtmp = "";

                                                            for(int i = 0;i<sym.length-1;i++){

                                                                symtmp  += ("#"+sym[i]);

                                                            }//증상출력

                                                            String meaning = sym[sym.length-1];

                                                            adapter.addItem(disName, symtmp,meaning,hospitalArr[finalJ]);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    });
                        }//for끝
                        return true;
                    }

                    if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL){

                    }
                    return false;
                }
                //key끝
            });
            search.setText(null);

        }
    }
