package edu.washington.hmask.quizdroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import java.util.Calendar;

/**
 * Created by huntermask on 5/13/15.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    AlarmManager alarmManager;
    EditTextPreference syncFrequencyPreference;
    EditTextPreference syncUrlPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);


        addPreferencesFromResource(R.xml.pref_data_sync);
        syncFrequencyPreference = (EditTextPreference) findPreference("sync_frequency");
        syncFrequencyPreference.setSummary(syncFrequencyPreference.getText());
        syncFrequencyPreference.setOnPreferenceChangeListener(this);

        syncUrlPreference = (EditTextPreference) findPreference("sync_url");
        syncUrlPreference.setSummary(syncUrlPreference.getText());
        syncUrlPreference.setOnPreferenceChangeListener(this);

        setUpdateInterval();
    }

    private void setUpdateInterval() {
        Intent i = new Intent(getActivity().getApplicationContext(), UpdateReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 0,
                i,
                PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pi);
        pi.cancel();
        i = new Intent(getActivity().getApplicationContext(), UpdateReceiver.class);
        i.putExtra("url", syncUrlPreference.getSummary().toString());
        pi = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 0,
                i,
                PendingIntent.FLAG_CANCEL_CURRENT);
        long interval = Long.parseLong(syncFrequencyPreference.getSummary().toString()) * 60 * 1000;
        alarmManager.setRepeating(AlarmManager.RTC, Calendar.getInstance().getTimeInMillis() + interval, interval, pi);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        preference.setSummary(value.toString());
        setUpdateInterval();

        return true;
    }
}
