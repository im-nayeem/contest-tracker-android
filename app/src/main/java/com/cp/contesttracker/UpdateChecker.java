package com.cp.contesttracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateChecker {

    private static final String TAG = "UpdateChecker";
    private Context context;
    private String apiUrl;

    public UpdateChecker(Context context, String apiUrl) {
        this.context = context;
        this.apiUrl = apiUrl;
    }

    public void checkForUpdate() {
        new CheckUpdateTask().execute(apiUrl);
    }

    private class CheckUpdateTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            String apiUrl = params[0];
            Request request = new Request.Builder().url(apiUrl).build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error while checking for updates: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            if (jsonResponse != null) {
                try {
                    JSONArray releases = new JSONArray(jsonResponse);
                    if (releases.length() > 0) {
                        JSONObject latestRelease = releases.getJSONObject(0);
                        String latestVersion = latestRelease.getString("tag_name");

                        if (isUpdateAvailable(latestVersion)) {
                            showUpdateDialog(latestVersion, latestRelease.getString("html_url"));
                        }
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
                }
            }
        }
    }

    private boolean isUpdateAvailable(String latestVersion) {
        String currentVersion = "v" + getAppVersion();
        return latestVersion.compareTo(currentVersion) > 0;
    }

    private void showUpdateDialog(String latestVersion, final String releaseUrl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("New Version Available");
        builder.setMessage("A new version (" + latestVersion + ") is available. Do you want to update now?");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(releaseUrl));
                context.startActivity(browserIntent);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private String getAppVersion() {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Error getting app version: " + e.getMessage());
            return "1.0"; // default(first release)
        }
    }
}
