package com.gorjoe.tunplmus.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.gorjoe.tunplmus.BuildConfig;
import com.gorjoe.tunplmus.databinding.FragmentDownloadBinding;
import com.yausername.youtubedl_android.DownloadProgressCallback;
import com.yausername.youtubedl_android.YoutubeDL;
import com.yausername.youtubedl_android.YoutubeDLRequest;
import java.io.File;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DownloadFragment extends Fragment {
    private FragmentDownloadBinding binding;

    private boolean downloading = false;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String processId = "MyDlProcess";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDownloadBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.btnDownload.setOnClickListener(v -> startDownload());
    }

    private final DownloadProgressCallback callback = new DownloadProgressCallback() {
        @Override
        public void onProgressUpdate(float progress, long etaInSeconds, String line) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    progressBar.setProgress((int) progress);
//                    tvDownloadStatus.setText(line);
                }
            });
        }
    };

    private void startDownload() {
        if (downloading) {
            Toast.makeText(getActivity(), "cannot start download. a download is already in progress", Toast.LENGTH_LONG).show();
            return;
        }

        String url = binding.etUrl.getText().toString().trim();
        if (TextUtils.isEmpty(url)) {
            binding.etUrl.setError("error");
            return;
        }

        YoutubeDLRequest request = new YoutubeDLRequest(url);
        File youtubeDLDir = getDownloadLocation();
//        File config = new File(youtubeDLDir, "config.txt");

//        request.addOption("--config-location", config.getAbsolutePath());
        request.addOption("--no-mtime");
        request.addOption("--downloader", "libaria2c.so");
        request.addOption("--external-downloader-args", "aria2c:\"--summary-interval=1\"");
//        request.addOption("-f", "bestaudio[ext=m4a]");
        request.addOption("--extract-audio");
        request.addOption("--audio-format", "mp3");
        request.addOption("-o", youtubeDLDir.getAbsolutePath() + "/%(title)s.%(ext)s");
        request.addOption("--download-archive", youtubeDLDir.getAbsolutePath() + "/downloaded.txt");
//        request.addOption("-no-overwrites");
//        request.addOption("--embed-thumbnail");
        request.addOption("--add-metadata");
        request.addOption("--no-mtime");

//        showStart();

        downloading = true;
        Disposable disposable = Observable.fromCallable(() -> YoutubeDL.getInstance().execute(request, processId, callback))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(youtubeDLResponse -> {
//                    pbLoading.setVisibility(View.GONE);
//                    progressBar.setProgress(100);
//                    tvDownloadStatus.setText("download done!");
//                    tvCommandOutput.setText(youtubeDLResponse.getOut()); // Log
                    Toast.makeText(getActivity(), "download successful", Toast.LENGTH_LONG).show();
                    Log.e("ytdl", "download successful");
                    downloading = false;
                }, e -> {
                    if (BuildConfig.DEBUG) Log.e("ytdl", "failed to download", e);
//                    pbLoading.setVisibility(View.GONE);
//                    tvDownloadStatus.setText("downlaod failed");
//                    tvCommandOutput.setText(e.getMessage()); // Log
                    Toast.makeText(getActivity(), "download failed", Toast.LENGTH_LONG).show();
                    downloading = false;
                });
        compositeDisposable.add(disposable);

    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @NonNull
    private File getDownloadLocation() {
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File youtubeDLDir = new File(downloadsDir, "youtubedl-android");
        if (!youtubeDLDir.exists()) youtubeDLDir.mkdir();
        return youtubeDLDir;
    }
}
