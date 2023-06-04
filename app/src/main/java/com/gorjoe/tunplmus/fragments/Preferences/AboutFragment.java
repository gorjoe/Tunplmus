package com.gorjoe.tunplmus.fragments.Preferences;

import android.os.Bundle;
import com.bluewhaleyt.common.ApplicationUtil;
import com.bluewhaleyt.component.preferences.CustomPreferenceFragment;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.gorjoe.tunplmus.R;

public class AboutFragment extends CustomPreferenceFragment {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_about, rootKey);
        init();
    }

    private void init() {
        try {
            var prefOpenSource = findPreference("btn_pref_open_source");
            var prefDeveloper = findPreference("btn_pref_developer");
            var prefTranslate = findPreference("btn_pref_translate");
            var prefVersion = findPreference("btn_pref_version");

            prefTranslate.setSummary(
                    getString(R.string.language_name) + " " +
                            getString(R.string.translate_by)
            );
            prefVersion.setSummary(ApplicationUtil.getAppVersionName(requireActivity()));

        } catch (Exception e) {
            SnackbarUtil.makeErrorSnackbar(requireActivity(), e.getMessage(), e.toString());
        }

    }

}