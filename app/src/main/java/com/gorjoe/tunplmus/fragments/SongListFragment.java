package com.gorjoe.tunplmus.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.gorjoe.tunplmus.R;

public class SongListFragment extends Fragment {

    private FragmentSongLisTBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSongListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionUtil.isAlreadyGrantedExternalStorageAccess()) {
            SharedPreferences sp = getSharedPreferences("directory", Context.MODE_PRIVATE);
            SongMediaStore.FilterOnlySongInSpecifyDirectory(this, sp);

            binding.lvSongList.setLayoutManager(linearLayoutManager);
            binding.lvSongList.setAdapter(songlistadapter);
            binding.lvSongList.getAdapter().notifyDataSetChanged();
        }
    }
}
