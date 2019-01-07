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

    public RoomContextHttpManager(ContextManagementActivity contextManagementActivity) {
        cma = contextManagementActivity;
    }

    public void init(Activity activity){
        queue = Volley.newRequestQueue(activity);
        queue.start();
    }
    ArrayList<String> roomid = new ArrayList<String>();


    public void getSpinnerInfo(){
        String url = CONTEXT_SERVER_URL+"rooms";
        final JsonArrayRequest contextRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i =0; i < response.length();i++){
                                JSONObject info = response.getJSONObject(i);
                                roomid.add(info.getString("id"));
                            }
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
                        System.out.println("something is wrong with getSpinnerInfo");
                        System.out.println("error:"+error);
                    }
                });
        queue.add(contextRequest);
    }

    public void retrieveRoomContextState(String room){
        String url = CONTEXT_SERVER_URL + "rooms/" + room +"/";

        final JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                                String id = response.getString("id").toString();
                                int lightLevel = Integer.parseInt(response.get("level").toString());
                                String status = response.getString("status").toString();
                                RoomContextState context = new RoomContextState(id, lightLevel, status);
                                cma.onUpdate(context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Some error to access URL : Room may not exists
                        System.out.println("something is wrong with retrieveRoomContextState");
                        System.out.println("error:"+error);
                    }
                });
        queue.add(contextRequest);
    }

    public void switchLight(RoomContextState state, String room){;
        String url = CONTEXT_SERVER_URL + "rooms/" + room +"/switch";

        final JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = response.getString("id").toString();
                            int lightLevel = Integer.parseInt(response.get("level").toString());
                            String status = response.getString("status").toString();
                            RoomContextState context = new RoomContextState(id, lightLevel, status);
                            cma.onUpdate(context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Some error to access URL : Room may not exists
                        System.out.println("something is wrong with switchLight");
                        System.out.println("error:"+error);
                    }
                });
        queue.add(contextRequest);

    }

    public void changeLightLevel(RoomContextState state, String room, String level){
        String url = CONTEXT_SERVER_URL + "rooms/" + room +"/changeLight/" + level;

        final JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = response.getString("id").toString();
                            int lightLevel = Integer.parseInt(response.get("level").toString());
                            String status = response.getString("status").toString();
                            RoomContextState context = new RoomContextState(id, lightLevel, status);
                            cma.onUpdate(context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Some error to access URL : Room may not exists
                        System.out.println("something is wrong with ChangeLightLevel");
                        System.out.println("error:"+error);
                    }
                });
        queue.add(contextRequest);
    }
}


