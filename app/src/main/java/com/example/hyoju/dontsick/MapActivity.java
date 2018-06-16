package com.example.hyoju.dontsick;

import android.app.FragmentManager;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    Document doc = null;
    public String hospital;
    public String info[][]=new String[0][0];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onClick(View view){
        GetXMLTask task = new GetXMLTask();
        task.execute("R.raw.hospital_api");
    }



    final Geocoder geocoder = new Geocoder(this);
    @Override
    public void onMapReady(final GoogleMap map) {

        for(int i=0;i<info.length;i++) {
            if (info[i][0].equals(hospital)) {
                List<Address> list = null;
                try {
                    list = geocoder.getFromLocationName(info[i][2], 10);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
                }
                if (list != null) {
                    if (list.size() == 0) {
                        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.52487, 126.92723)));
                    }else {
                        Address addr = list.get(0);
                        double lat = addr.getLatitude();
                        double lon = addr.getLongitude();
                        MarkerOptions markerOptions = new MarkerOptions();

                        markerOptions
                                .position(new LatLng(lat, lon))
                                .title(info[i][1])
                                .snippet(info[i][2]);
                        map.addMarker(markerOptions);
                        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(50, 50)));
                    }
                }
            }
        }
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

    private class GetXMLTask extends AsyncTask<String, Void, Document>{

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try{
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();
            }catch(Exception e){
                Toast.makeText(getBaseContext(),"Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        protected void onPostExecute(Document doc){

            NodeList nodeList = doc.getElementsByTagName("Row");
            for(int i=0;i<nodeList.getLength();i++){
                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;

                NodeList nameList = fstElmnt.getElementsByTagName("구분");
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                info[i][0] = ((Node) nameList.item(0)).getNodeValue();

                NodeList hosList = fstElmnt.getElementsByTagName("의료기관명");
                Element hosElement = (Element) hosList.item(0);
                hosList = hosElement.getChildNodes();
                info[i][1] = ((Node) hosList.item(0)).getNodeValue();

                NodeList addList = fstElmnt.getElementsByTagName("의료기관주소_도로명_");
                Element addElement = (Element) addList.item(0);
                addList = addElement.getChildNodes();
                info[i][2] = ((Node) addList.item(0)).getNodeValue();
            }
            super.onPostExecute(doc);
        }
    }
}
