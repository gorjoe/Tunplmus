package com.gorjoe.tunplmus.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bluewhaleyt.common.PermissionUtil;
import com.gorjoe.tunplmus.R;
import com.gorjoe.tunplmus.databinding.FragmentSongslistBinding;

import java.util.List;

import static com.gorjoe.tunplmus.Utils.SongMediaStore.songlistadapter;

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
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
            binding.lvSongList.setLayoutManager(linearLayoutManager);
            binding.lvSongList.setAdapter(songlistadapter);
            binding.lvSongList.getAdapter().notifyDataSetChanged();
        }
    }
}
