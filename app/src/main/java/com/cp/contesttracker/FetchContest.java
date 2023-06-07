package com.cp.contesttracker;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchContest {

    private List<Contest> contestList = null;

    public void fetchAPI(final Context context, final FetchCallBack callBack) {


        try {


            String url = "https://clist.by/api/v2/contest/?format=json&upcoming=true&format_time=true&username=html_programmer&api_key=0df77c63b2ca703a58c65409734f69654e38fddf";

            RequestQueue requestQueue = Volley.newRequestQueue(context);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("objects");
                        JSONObject temp = null;

//                        int last = Math.min(jsonArray.length(),50);

                        for(int i=0; i<3; i++)
                        {
                           temp = (JSONObject) jsonArray.get(i);
                           Log.e("Res: ---------------- ", temp.getString("event"));
//                            Toast.makeText(context,temp.getString("event"),Toast.LENGTH_LONG);
                           contestList.add(new Contest("Contest","time","host"));
                        }

                        callBack.onContestFetch(contestList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            requestQueue.add(request);

        }
        catch (Exception e){
            Log.e("Error: ",e.getMessage());
            e.printStackTrace();
        }
    }
}
