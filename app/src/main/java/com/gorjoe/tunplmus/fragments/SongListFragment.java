package com.gorjoe.tunplmus.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluewhaleyt.common.PermissionUtil;
import com.gorjoe.tunplmus.R;
import com.gorjoe.tunplmus.Utils.SongMediaStore;
import com.gorjoe.tunplmus.adapter.SongListAdapter;
import com.gorjoe.tunplmus.databinding.FragmentSongslistBinding;

public class SongListFragment extends Fragment {

    private FragmentSongslistBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSongslistBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (PermissionUtil.isAlreadyGrantedExternalStorageAccess()) {
            SharedPreferences sp = requireContext().getSharedPreferences("directory", Context.MODE_PRIVATE);
            SongMediaStore.FilterOnlySongInSpecifyDirectory(requireActivity(), sp);

            var linearLayoutManager = new LinearLayoutManager(requireContext());

            binding.lvSongsList.setLayoutManager(linearLayoutManager);
            binding.lvSongsList.setAdapter(songlistadapter);
            binding.lvSongsList.getAdapter().notifyDataSetChanged();
        }
    }
}
