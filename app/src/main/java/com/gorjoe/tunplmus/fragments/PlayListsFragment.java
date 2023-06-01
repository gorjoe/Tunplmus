package com.gorjoe.tunplmus.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import com.gorjoe.tunplmus.adapter.PlayListsAdapter;
import com.gorjoe.tunplmus.adapter.SongListAdapter;
import com.gorjoe.tunplmus.databinding.FragmentPlaylistsBinding;
import com.gorjoe.tunplmus.models.PlayListsModel;
import com.gorjoe.tunplmus.models.SongModel;

import java.util.ArrayList;

public class PlayListsFragment extends Fragment {

    private FragmentPlaylistsBinding binding;

    private static ArrayList<PlayListsModel> list = new ArrayList<>();
    public static PlayListsAdapter playlistadapter = new PlayListsAdapter(list);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlaylistsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                PlayListsAdapter playlistadapter = new PlayListsAdapter(list);

                list.add(new PlayListsModel());
                list.get(list.size() - 1).setName("new list " + list.size());
                list.get(list.size() - 1).setTotal(0);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.lvPlayList.setLayoutManager(linearLayoutManager);
        binding.lvPlayList.setAdapter(playlistadapter);
        binding.lvPlayList.getAdapter().notifyDataSetChanged();
    }
}
