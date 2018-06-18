package com.example.hyoju.dontsick;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    Document doc = null;
    public String hospital;
    ArrayList<Hospital> list;
    String add="";
    GoogleMap googlemap;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        hospital = intent.getExtras().getString("HospitalKey");

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        list = new ArrayList<Hospital>();
        XmlPullParser res = getResources().getXml((R.xml.hospital_api));
        int eventType = 0;
        String sTag="";
        Hospital myHopital = null;

        boolean check_type = false, check_name=false, check_add=false, check_phone=false;
        try {
            eventType = res.getEventType();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }while(eventType != XmlPullParser.END_DOCUMENT){
            boolean check = false;
            switch(eventType){
                case XmlPullParser.START_DOCUMENT:
                    sTag = res.getName();
                    break;
                case XmlPullParser.START_TAG:
                    sTag = res.getName();
                    if(sTag.equals("Row")){
                        myHopital = new Hospital();
                    }else if(sTag.equals("구분")){
                        check_type = true;
                    }else if(sTag.equals("의료기관명")){
                       check_name = true;
                    }else if(sTag.equals("의료기관전화번호")){
                        check_phone= true;
                    }else if(sTag.equals("의료기관주소_도로명_")){
                        check_add = true;
                    }
                    break;
                case XmlPullParser.TEXT:
                    if(check_name){
                        myHopital.name = res.getText();
                        check_name = false;
                    }if(check_phone){
                    myHopital.phone = res.getText();
                    check_phone = false;
                }if(check_add){
                    myHopital.add = res.getText();
                    check_add = false;
                }if(check_type){
                    myHopital.type = res.getText();
                    check_type = false;
                }
                    break;
                case XmlPullParser.END_TAG:
                    sTag = res.getName();
                    if(sTag.equalsIgnoreCase("Row")  && myHopital != null ){
                        list.add(myHopital);
                        myHopital = null;
                    }
                    break;
                case XmlPullParser.END_DOCUMENT:
                    break;
            }
            try {
                eventType=res.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("size", String.valueOf(list.size()));
    }


    Geocoder geocoder;
    @Override
    public void onMapReady(final GoogleMap map) {
        googlemap = map;
        geocoder = new Geocoder(this);
        for(int i=0;i<list.size();i++) {
            if (list.get(i).getType().equals(this.hospital)) {//병원 종류가 같으면
                List<Address> Llist = null;
                try {
                    Llist = geocoder.getFromLocationName(list.get(i).getAdd(), 20);//주소 1이 병원이름 0이 구분
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
                }
                if (Llist != null) {
                    if (Llist.size() == 0) {
                        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.544905, 127.078145)));
                    }else {
                        Address addr = Llist.get(0);
                        double lat = addr.getLatitude();
                        double lon = addr.getLongitude();
                        MarkerOptions markerOptions = new MarkerOptions();

                        markerOptions
                                .position(new LatLng(lat, lon))
                                .title(list.get(i).getName())
                                .snippet(list.get(i).getAdd());

                        map.addMarker(markerOptions);
                        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.544905, 127.078145)));

                        googlemap.setOnMarkerClickListener(markerClickListener);
                    }
                }else{
                    map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.544905, 127.078145)));
                }
            }
        }
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

    GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            add = marker.getSnippet();
            Log.d("add",add);
            return false;
        }
    };
}