package com.gorjoe.tunplmus.Utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.gson.Gson;
import com.gorjoe.tunplmus.Song;
import com.gorjoe.tunplmus.adapter.SongListAdapter;
import com.gorjoe.tunplmus.models.SongModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SongMediaStore {
    public static ArrayList<Song> audioFiles = new ArrayList<Song>();
    private static ArrayList<SongModel> list = new ArrayList<>();
    public static ArrayList<ArrayList<String>> spSongList = new ArrayList<>();
    public static SongListAdapter songlistadapter = new SongListAdapter(list);

    public static ArrayList<Song> getAudioFilesArray() {
        return audioFiles;
    }

    public static List<Song> getAllAudioFiles(Activity activity, String dir) {
        audioFiles.clear();

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM};

        try (Cursor cursor = activity.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null)) {
            if (cursor != null) {
                int idCol = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int titleCol = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int artistCol = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int durationCol = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                int dataCol = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                int albumCol = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

                while (cursor.moveToNext()) {
                    long id = cursor.getLong(idCol);
                    String title = cursor.getString(titleCol);
                    String artist = cursor.getString(artistCol);
                    long duration = cursor.getLong(durationCol);
                    String path = cursor.getString(dataCol);
                    String album = cursor.getString(albumCol);
                    Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);

                    if (path.startsWith(dir)) {
                        audioFiles.add(new Song(id, title, artist, duration, path, album, contentUri));

                        ArrayList<String> songitem = new ArrayList<>();
                        songitem.add(String.valueOf(id));
                        songitem.add(title);
                        songitem.add(artist);
                        songitem.add(String.valueOf(duration));
                        songitem.add(path);
                        songitem.add(album);
                        songitem.add(String.valueOf(contentUri));

                        spSongList.add(songitem);
                    }
                }
            }
        }
        return audioFiles;
    }

    public static void FilterOnlySongInSpecifyDirectory(Activity activity, SharedPreferences sp, SharedPreferences sl) {
        list = new ArrayList<>();
        songlistadapter = new SongListAdapter(list);
        if (songlistadapter.getItemCount() == 0) {
            // Retrieving the value using its keys the file name
            // must be same in both saving and retrieving the data

            String dir = sp.getString("directory", "unknown");
            Toast.makeText(activity, "dir: " + dir, Toast.LENGTH_LONG).show();
            Log.e("Test", "dir: " + dir);

            if (!dir.equals("unknown")) {
                // loop file to screen
                ArrayList<String> files = new ArrayList<>();

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
                linearLayoutManager.setStackFromEnd(true);
                linearLayoutManager.setReverseLayout(true);

                getAllAudioFiles(activity, dir);
                Collections.sort(audioFiles, Comparator.comparing(Song::getTitle));

                for (int i = 0; i < audioFiles.size(); i++) {
                    Song currSong = audioFiles.get(i);

                    list.add(new SongModel());
                    list.get(i).setName(currSong.getTitle());
                    list.get(i).setAuthor(currSong.getArtist());
                    list.get(i).setDuration(currSong.getDuration());
                }

                Toast.makeText(activity, list.size() + " songs loaded", Toast.LENGTH_SHORT).show();
                Log.e("songlist", String.valueOf(spSongList));

                Gson gson = new Gson();
                String json = gson.toJson(spSongList);

                sl.edit().putString("SongList", json).apply();

            } else {
                Toast.makeText(activity, "Song Directory Not Selected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
