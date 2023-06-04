package com.gorjoe.tunplmus.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.snackbar.Snackbar;
import com.gorjoe.tunplmus.adapter.PlayListsAdapter;
import com.gorjoe.tunplmus.databinding.FragmentPlaylistsBinding;
import com.gorjoe.tunplmus.models.PlayListsModel;

import java.util.ArrayList;

public class PlayListsFragment extends Fragment {
    private FragmentPlaylistsBinding binding;

    private static final ArrayList<PlayListsModel> list = new ArrayList<>();
    public static PlayListsAdapter playlistAdapter = new PlayListsAdapter(list);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlaylistsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.fab.setOnClickListener(view -> {
            Snackbar.make(view, "Playlist added", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            list.add(new PlayListsModel());
            list.get(list.size() - 1).setName("new list " + list.size());
            list.get(list.size() - 1).setTotal(0);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
            binding.lvPlayList.setLayoutManager(linearLayoutManager);
            binding.lvPlayList.setAdapter(playlistAdapter);
            binding.lvPlayList.getAdapter().notifyDataSetChanged();
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.lvPlayList.setLayoutManager(linearLayoutManager);
        binding.lvPlayList.setAdapter(playlistAdapter);
        binding.lvPlayList.getAdapter().notifyDataSetChanged();
    }
}
