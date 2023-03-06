//package com.gorjoe.tunplmus;
//
//import android.app.Activity;
//import android.content.ContentUris;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import com.bluewhaleyt.common.CommonUtil;
//import com.bluewhaleyt.common.PermissionUtil;
//import com.bluewhaleyt.crashdebugger.CrashDebugger;
//import com.bluewhaleyt.moderndialog.ModernDialog;
//import com.gorjoe.tunplmus.Utils.DialogUtils;
//import com.gorjoe.tunplmus.adapter.SongListAdapter;
//import com.gorjoe.tunplmus.databinding.ActivitySongListBinding;
//import com.gorjoe.tunplmus.models.SongModel;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import nl.joery.animatedbottombar.AnimatedBottomBar;
//
//public class SongListActivity extends AppCompatActivity {
//
//    private ActivitySongListBinding binding;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        CrashDebugger.init(this);
//        binding = ActivitySongListBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        init();
//    }
//
//    private void init() {
//        CommonUtil.setNavigationBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);
//        CommonUtil.setStatusBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_DEFAULT_TOOLBAR);
//        getSupportActionBar().setTitle("Tunplmus");
//        binding.tvSongName.setSelected(true);
//        binding.tvSongName.setSingleLine();
//        binding.layoutCurrentSong.setOnClickListener(v -> {
////            startActivity(new Intent(this, MediaPlayerActivity.class));
//            Intent intent = new Intent(this, MediaPlayerActivity.class);
//            startActivity(intent);
//            overridePendingTransition( R.anim.slide_in_up, 0 );
//        });
//
//        binding.bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
//            @Override
//            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {
//                Log.d("bottom_bar", "Selected index: " + newIndex + ", title: " + newTab.getTitle());
//            }
//
//            @Override
//            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {
////                Log.d("bottom_bar", "Reselected index: " + index + ", title: " + tab.getTitle());
//            }
//        });
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_download:
//                startActivity(new Intent(this, DownloadActivity.class));
//                break;
//            case R.id.menu_history:
//                startActivity(new Intent(this, DownloadHistoryActivity.class));
//                break;
//            case R.id.menu_settings:
//                startActivity(new Intent(this, SettingsActivity.class));
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}