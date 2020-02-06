package com.example.routes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    private HashMap<String, String> getPlace(JSONObject jsonObject) {
        HashMap<String, String> place = new HashMap<>();
        String placeName = "N/A";
        String vicinity = "N/A";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {
            if (!jsonObject.isNull("name")) {
                placeName = jsonObject.getString("name");
            }
            if (!jsonObject.isNull("vicinity")) {
                vicinity = jsonObject.getString("vicinity");
            }

            latitude = jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = jsonObject.getString("reference");

            place.put("placeName", placeName);
            place.put("vicinity", vicinity);
            place.put("lat", latitude);
            place.put("lng", longitude);
            place.put("reference", reference);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return place;
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray){
        int count = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<>();
        HashMap<String, String> place = null;

        for (int i = 0; i < count; i++){
            try {
                place = getPlace( (JSONObject) jsonArray.get(i));
                placesList.add(place);

            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return placesList;

    }

    public List<HashMap<String, String>> parseData(String jsonData){
        JSONArray jsonArray = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");

        } catch (JSONException e){
            e.printStackTrace();
        }

        return getPlaces(jsonArray);
    }

    public HashMap<String, String> parseDistance(String jsonData){
        JSONArray jsonArray = null;
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");

        } catch(JSONException e){
            e.printStackTrace();
        }

        return getDuration(jsonArray);

    }


    private HashMap<String, String> getDuration(JSONArray directionJSONData){
        HashMap<String, String> directionMap = new HashMap<>();
        String duration = "", distance = "";

        try {
            duration = directionJSONData.getJSONObject(0).getJSONObject("duration").getString("text");
            distance = directionJSONData.getJSONObject(0).getJSONObject("distance").getString("text");

            directionMap.put("duration", duration);
            directionMap.put("distance", distance);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return directionMap;
    }
}

















