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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FetchContest {

    private HashMap<String,List<Contest>> contestList = null;

    public void fetchAPI(final Context context, final FetchCallBack callBack) {

        contestList  = new HashMap<>();

        try {
            final String url = "https://clist.by/api/v3/contest/?format=json&upcoming=true&format_time=true&limit=3000&username=html_programmer&api_key=0df77c63b2ca703a58c65409734f69654e38fddf";

            RequestQueue requestQueue = Volley.newRequestQueue(context);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                            JSONArray jsonArray = response.getJSONArray("objects");
                            JSONObject temp = null;

                            int last = jsonArray.length();

                        HashMap<String, String> linkToHostName = Contest.getLinkToHostName();

                        contestList.put("All", new ArrayList<Contest>());
                        for(Map.Entry<String, String>entry : linkToHostName.entrySet())
                        {
                            contestList.put(entry.getValue(), new ArrayList<Contest>());
                        }


                        for(int i=0; i<last; i++)
                            {
                               temp = (JSONObject) jsonArray.get(i);

                               Date currentDate = Utility.getCurrentDate();


                               if(linkToHostName.containsKey(temp.getString("host")))
                               {
                                   if(Utility.ifDateIsOver(currentDate, Utility.parseTimeStamp(temp.getString("start"))))
                                        continue;

                                   contestList.get(linkToHostName.get(temp.getString("host")))
                                           .add( new Contest( temp.getString("event"),
                                                   Utility.parseTimeStamp(temp.getString("start")),
                                                                temp.getString("host"), temp.getString("href")));
                                   contestList.get("All")
                                           .add( new Contest( temp.getString("event"),
                                                   Utility.parseTimeStamp(temp.getString("start")),
                                                                temp.getString("host"), temp.getString("href")));
                               }

                            }



                            callBack.onContestFetch(contestList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context,"Couldn't Fetch Data! Check Your Internet Connection.",Toast.LENGTH_LONG).show();
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
