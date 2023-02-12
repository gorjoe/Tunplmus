package com.gorjoe.tunplmus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.gorjoe.tunplmus.adapter.HistoryListAdapter;
import com.gorjoe.tunplmus.databinding.ActivityDownloadHistoryBinding;
import com.gorjoe.tunplmus.models.HistoryModel;

import java.time.LocalTime;
import java.util.ArrayList;

public class DownloadHistoryActivity extends AppCompatActivity {

    private ActivityDownloadHistoryBinding binding;

    private ArrayList<HistoryModel> list;
    private HistoryListAdapter adapter;
    private HistoryModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        binding = ActivityDownloadHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {

        CommonUtil.setNavigationBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);
        CommonUtil.setStatusBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_DEFAULT_TOOLBAR);

        getSupportActionBar().setTitle("History");

        showDownloadHistory();

    }

    private void showDownloadHistory() {

        list = new ArrayList<>();
        adapter = new HistoryListAdapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        binding.rvList.setLayoutManager(linearLayoutManager);
        binding.rvList.setAdapter(adapter);

        // test


        list.add(new HistoryModel());
        list.get(0).setName("Hello world");
        list.get(0).setTime(LocalTime.now().toString());

    }

}