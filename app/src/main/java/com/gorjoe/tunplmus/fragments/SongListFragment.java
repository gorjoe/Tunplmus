package com.gorjoe.tunplmus.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bluewhaleyt.common.PermissionUtil;
import com.gorjoe.tunplmus.Utils.SongMediaStore;
import com.gorjoe.tunplmus.databinding.FragmentSongslistBinding;
import java.util.ArrayList;
import static com.gorjoe.tunplmus.Utils.SongMediaStore.songlistadapter;

public class SongListFragment extends Fragment {

    private FragmentSongslistBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSongslistBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (PermissionUtil.isAlreadyGrantedExternalStorageAccess()) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
            binding.lvSongList.setLayoutManager(linearLayoutManager);
            binding.lvSongList.setAdapter(songlistadapter);
            binding.lvSongList.getAdapter().notifyDataSetChanged();

            SharedPreferences sl = getContext().getSharedPreferences("SongList", Context.MODE_PRIVATE);
            ArrayList<ArrayList<String>> playlist = SongMediaStore.fetchSongFromSharePreferences(sl);
            if (playlist == null || playlist.isEmpty()) {
                SharedPreferences sp = getContext().getSharedPreferences("directory", Context.MODE_PRIVATE);
                SongMediaStore.getAllAudioFiles(getActivity(), sp.getString("directory", "unknown"), sl);

                binding.lvSongList.setLayoutManager(linearLayoutManager);
                binding.lvSongList.setAdapter(songlistadapter);
                binding.lvSongList.getAdapter().notifyDataSetChanged();
            }

            SongMediaStore.renderSongList(sl, getActivity());
        }
    }
}
