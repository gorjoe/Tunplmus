package com.gorjoe.tunplmus.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.gorjoe.tunplmus.databinding.FragmentDownloadBinding;

public class DownloadFragment extends Fragment {
    private FragmentDownloadBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDownloadBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.btnDownload.setOnClickListener(v -> download());
    }

    private void download() {
         SnackbarUtil.makeSnackbar(getActivity(), "button clicked");
    }
}
