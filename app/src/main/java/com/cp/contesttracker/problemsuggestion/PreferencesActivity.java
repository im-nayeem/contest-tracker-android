package com.cp.contesttracker.problemsuggestion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cp.contesttracker.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class PreferencesActivity extends AppCompatActivity {
    private TextView codeforces, spoj, codechef;
    private CheckBox checkBoxCF, checkBoxCodechef, checkBoxAtcoder, checkBoxToph;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Settings & Preferences");

        codeforces = findViewById(R.id.editTextCF);
        codechef = findViewById(R.id.editTextCodechef);
        spoj = findViewById(R.id.editTextSPOJ);
        saveButton = findViewById(R.id.saveButton);

        checkBoxCF = findViewById(R.id.checkBoxCF);
        checkBoxCodechef = findViewById(R.id.checkBoxCodechef);
        checkBoxAtcoder = findViewById(R.id.checkBoxAtcoder);
        checkBoxToph = findViewById(R.id.checkBoxToph);

        loadSavedPreference();

        this.addEventListener();
    }

    // add event listener on save button, save to shared preferences
    private void addEventListener() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject ojHandles = new JSONObject();
                JSONObject contestNotification = new JSONObject();
                try {
                    ojHandles.put("cf", codeforces.getText());
                    ojHandles.put("cc", codechef.getText());
                    ojHandles.put("sp", spoj.getText());

                    contestNotification.put("codeforces", checkBoxCF.isChecked());
                    contestNotification.put("codechef", checkBoxCodechef.isChecked());
                    contestNotification.put("atcoder", checkBoxAtcoder.isChecked());
                    contestNotification.put("toph", checkBoxToph.isChecked());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AppPreferences preferences = new AppPreferences(PreferencesActivity.this);
                preferences.saveOjHandles(ojHandles);
                preferences.saveNotificationPreference(contestNotification);

                finish();
            }
        });
    }

    // load saved preferences in view
    private void loadSavedPreference() {
        AppPreferences preferences = new AppPreferences(PreferencesActivity.this);
        JSONObject ojHandles = preferences.getOjHandles();
        JSONObject notificationPrefs = preferences.getNotificationPrefs();

        try {
            this.codeforces.setText(ojHandles.get("cf").toString());
            this.codechef.setText(ojHandles.get("cc").toString());
            this.spoj.setText(ojHandles.get("sp").toString());

            this.checkBoxCF.setChecked((Boolean) notificationPrefs.get("codeforces"));
            this.checkBoxCodechef.setChecked((Boolean) notificationPrefs.get("codechef"));
            this.checkBoxAtcoder.setChecked((Boolean) notificationPrefs.get("atcoder"));
            this.checkBoxToph.setChecked((Boolean) notificationPrefs.get("toph"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
