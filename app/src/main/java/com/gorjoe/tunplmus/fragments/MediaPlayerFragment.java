package com.gorjoe.tunplmus.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.gorjoe.tunplmus.R;
import com.gorjoe.tunplmus.databinding.FragmentDownloadBinding;
import com.gorjoe.tunplmus.databinding.FragmentMediaPlayerBinding;

public class MediaPlayerFragment extends Fragment {

    private FragmentMediaPlayerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMediaPlayerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}
