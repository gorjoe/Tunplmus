package com.gorjoe.tunplmus.fragments.Preferences;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.Preference;
import com.bluewhaleyt.common.IntentUtil;
import com.bluewhaleyt.component.preferences.CustomPreferenceFragment;
import com.gorjoe.tunplmus.R;
import com.gorjoe.tunplmus.fragments.Preferences.AboutFragment;
import com.gorjoe.tunplmus.fragments.Preferences.AppFragment;

public class SettingsFragment extends CustomPreferenceFragment {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        super.onCreatePreferences(savedInstanceState, rootKey);
        setPreferencesFromResource(R.xml.preferences_main, rootKey);
        init();
    }

    private void init() {
        try {
            var btnPrefApp = findPreference("btn_pref_application");
            var btnPrefEditor = findPreference("btn_pref_editor");
            var btnPrefCodeStyle = findPreference("btn_pref_coding_style");
            var btnPrefAbout = findPreference("btn_pref_about");

            intentFragment(btnPrefApp, new AppFragment());
            intentFragment(btnPrefAbout, new AboutFragment());

        } catch (Exception e) {
            Log.e("preferenceBtn", e.getMessage());
        }

    }

    private void intentFragment(Preference pref, Fragment fragment) {
        pref.setOnPreferenceClickListener(preference -> {
            FrameLayout fl = requireActivity().findViewById(R.id.fragment_container);
            fl.removeAllViews(); // Issue: try to fix overlapping fragments.
            IntentUtil.intentFragment(requireActivity(), fragment);
            return true;
        });
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
