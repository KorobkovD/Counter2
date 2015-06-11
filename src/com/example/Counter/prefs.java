package com.example.Counter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

/**
 * Created by Denis on 30.08.2014.
 */
public class prefs extends PreferenceActivity {

    public CheckBoxPreference cbpCount, cbpSound;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        cbpCount  = (CheckBoxPreference)findPreference("saveCount");
        cbpSound = (CheckBoxPreference)findPreference("soundSwitch");
    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor editor = main.mSettings.edit();
        //-----------------------------------
        if (cbpCount.isChecked())
            editor.putBoolean(String.valueOf(main.APP_PREFERENCES_COUNTERFLAG), true);
        else
            editor.putBoolean(String.valueOf(main.APP_PREFERENCES_COUNTERFLAG), false);
        //-----------------------------------
        if (cbpSound.isChecked())
            editor.putBoolean(String.valueOf(main.APP_PREFERENCES_SOUNDFLAG), true);
        else
            editor.putBoolean(String.valueOf(main.APP_PREFERENCES_SOUNDFLAG), false);
        //-----------------------------------
        editor.apply();
    }
}