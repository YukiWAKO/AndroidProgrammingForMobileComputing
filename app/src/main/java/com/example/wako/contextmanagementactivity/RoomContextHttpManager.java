package com.example.wako.contextmanagementactivity;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
*  place where to manage all the HTTP request
*/

public class RoomContextHttpManager {

    public ContextManagementActivity cma;
    RequestQueue queue ;
    final String CONTEXT_SERVER_URL = "https://fair-corp.cleverapps.io/api/";
    public void init(Activity activity){
        queue = Volley.newRequestQueue(activity);
        queue.start();
    }
    ArrayList<String> roomid = new ArrayList<String>();

    public void getSpinnerInfo(){
        System.out.println("関数入った");
        String url = CONTEXT_SERVER_URL+"rooms";
        System.out.println("url "+url);
        final JsonArrayRequest contextRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("通信成功");
                        try{
                            for(int i =0; i < response.length();i++){
                                JSONObject info = response.getJSONObject(i);
                                roomid.add(info.getString("id"));
                            }
                            System.out.println("roomid "+roomid);
                            cma.createSpinner(roomid);
                        }catch(JSONException e){
                            e.printStackTrace();
                            System.out.println("e:"+e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Some error to access URL : Room may not exists
                        System.out.println("something must be wrong, error happening on volley");
                        System.out.println("error:"+error);
                    }
                });
        queue.add(contextRequest);
    }

    public void retrieveRoomContextState(String room){
        String url = CONTEXT_SERVER_URL + "lights/" + room +"/";

        final JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("response:"+response);
                                String id = response.getString("id").toString();
                                int lightLevel = Integer.parseInt(response.get("level").toString());
                                String lightStatus = response.get("status").toString();
                                RoomContextState context = new RoomContextState(id,lightStatus,lightLevel);
                                cma.onUpdate(context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Some error to access URL : Room may not exists
                        System.out.println("something must be wrong, error happening");
                        System.out.println("error:"+error);
                    }
                });
        queue.add(contextRequest);
    }

    public void switchLight(RoomContextState state, String room){
        //ちょっとこれ正しいかわからない
        String url = CONTEXT_SERVER_URL + "lights/" + room +"/switch";

        final JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = response.getString("id").toString();
                            int lightlevel = Integer.parseInt(response.getJSONObject("light").get("level").toString());
                            String lightStatus = response.getJSONObject("light").get("status").toString();
                            //do something with results
                            RoomContextState context = new RoomContextState(id,lightStatus,lightlevel);
                            //notify main activity for update
                            cma.onUpdate(context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Some error to access URL : Room may not exists
                    }
                });
        queue.add(contextRequest);

    }
}


