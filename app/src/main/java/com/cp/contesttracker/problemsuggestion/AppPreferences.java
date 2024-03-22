package com.cp.contesttracker.problemsuggestion;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class AppPreferences {
    private Context context = null;
    private SharedPreferences sharedPref = null;
    private JSONObject ojHandles = null;
    private JSONObject notificationPrefs = null;
    private String recommenderUrl = null;

    public AppPreferences(Context context) {
        this.context = context;
        this.sharedPref = context.getSharedPreferences("preference", Context.MODE_PRIVATE);
        try {
            this.ojHandles = new JSONObject(sharedPref.getString("ojHandles", "{}"));
            this.notificationPrefs = new JSONObject(sharedPref.getString("notificationPrefs", "{}"));
            this.recommenderUrl = sharedPref.getString("recommenderUrl", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveOjHandles(JSONObject ojHandles) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("ojHandles", ojHandles.toString());

        try {
            this.recommenderUrl = "";
            if (!ojHandles.getString("cf").trim().equals(""))
                this.recommenderUrl += "cf/" + ojHandles.getString("cf").trim() + "+";
            if (!ojHandles.getString("cc").trim().equals(""))
                this.recommenderUrl += "cc/" + ojHandles.getString("cc").trim() + "+";
            if (!ojHandles.getString("sp").trim().equals(""))
                this.recommenderUrl += "sp/" + ojHandles.getString("sp").trim();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        editor.putString("recommenderUrl", this.recommenderUrl);
        editor.apply();
    }

    public void saveNotificationPreference(JSONObject notificationPrefs) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("notificationPrefs", notificationPrefs.toString());
        editor.apply();
    }

    public JSONObject getOjHandles() {
        return ojHandles;
    }

    public JSONObject getNotificationPrefs() {
        return notificationPrefs;
    }

    public String getRecommenderUrl() {
        if (this.recommenderUrl.equals(""))
            return recommenderUrl;
        return "https://recommender.codedrills.io/profile?handles=" + this.recommenderUrl;
    }
}
