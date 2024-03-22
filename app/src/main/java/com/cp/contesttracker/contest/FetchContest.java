package com.cp.contesttracker.contest;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cp.contesttracker.BuildConfig;
import com.cp.contesttracker.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FetchContest {

    private HashMap<String, List<Contest>> contestList = null;

    public void fetchAPI(final Context context, final FetchCallBack callBack) {

        contestList = new HashMap<>();

        try {
            String url = BuildConfig.API_KEY;

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
                        for (Map.Entry<String, String> entry : linkToHostName.entrySet()) {
                            contestList.put(entry.getValue(), new ArrayList<Contest>());
                        }


                        for (int i = 0; i < last; i++) {
                            temp = (JSONObject) jsonArray.get(i);

                            Date currentDate = Utility.getCurrentDateInGMT();

                            if (linkToHostName.containsKey(temp.getString("host"))) {
                                if (Utility.ifDateIsOver(currentDate, Utility.parseTimeStamp(temp.getString("start"))))
                                    continue;

                                contestList.get(linkToHostName.get(temp.getString("host")))
                                        .add(new Contest(temp.getString("event"),
                                                Utility.parseTimeStamp(temp.getString("start")),
                                                Long.parseLong(temp.getString("duration")),
                                                temp.getString("host"), temp.getString("href"),
                                                temp.getString("id")));
                                contestList.get("All")
                                        .add(new Contest(temp.getString("event"),
                                                Utility.parseTimeStamp(temp.getString("start")),
                                                Long.parseLong(temp.getString("duration")),
                                                temp.getString("host"), temp.getString("href"),
                                                temp.getString("id")));
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
                    Utility.showDialogueMessage(context, "Error Occurred!", "Couldn't fetch contest information. Check your Internet connection and try again!");
                    error.printStackTrace();
                }
            });

            requestQueue.add(request);

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            e.printStackTrace();
        }
    }


}
