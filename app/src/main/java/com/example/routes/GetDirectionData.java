package com.example.routes;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class GetDirectionData extends AsyncTask<Object, String, String> {

    String directionData, url;
    GoogleMap mMap;

    String distance, duration;

    LatLng latLng;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        latLng = (LatLng) objects[2];

        FetchURL fetchURL= new FetchURL();
        try{
            directionData = fetchURL.readURL(url);
        } catch (IOException e){
            e.printStackTrace();
        }

        return directionData;
    }

    @Override
    protected void onPostExecute(String s) {
        HashMap<String, String> distanceHashMap = null;
        DataParser distanceParser = new DataParser();
        distanceHashMap = distanceParser.parseDistance(s);

        distance = distanceHashMap.get("distance");
        duration = distanceHashMap.get("duration");

        mMap.clear();

        //create new marker with new title and snippet
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .draggable(true)
                .title("Duration: " + duration)
                .snippet("Distance: " + distance);
        mMap.addMarker(markerOptions);

    }





}

























