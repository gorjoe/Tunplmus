package com.gorjoe.tunplmus.Utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gorjoe.tunplmus.Song;
import com.gorjoe.tunplmus.adapter.SongListAdapter;
import com.gorjoe.tunplmus.models.SongModel;
import java.util.ArrayList;
import java.util.Collections;

public class SongMediaStore {
    public static ArrayList<Song> audioFiles = new ArrayList<>();
    private static ArrayList<SongModel> list = new ArrayList<>();
    public static ArrayList<ArrayList<String>> spSongList = new ArrayList<>();
    public static SongListAdapter songlistadapter = new SongListAdapter(list);

    public static ArrayList<Song> getAudioFilesArray() {
        return audioFiles;
    }

    public static void getAllAudioFiles(Activity activity, String dir, SharedPreferences sl) {
        audioFiles.clear();
        spSongList.clear();

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

                        ArrayList<String> songItem = new ArrayList<>();
                        songItem.add(String.valueOf(id));
                        songItem.add(title);
                        songItem.add(artist);
                        songItem.add(String.valueOf(duration));
                        songItem.add(path);
                        songItem.add(album);
                        songItem.add(String.valueOf(contentUri));

                        spSongList.add(songItem);
                    }
                }
                Gson gson = new Gson();
                String json = gson.toJson(spSongList);
                sl.edit().remove("SongList").apply();
                sl.edit().putString("SongList", json).apply();
            }
        }
    }

    public static void renderSongList(SharedPreferences sp, Activity activity) {
        try {
            list = new ArrayList<>();
            songlistadapter = new SongListAdapter(list);
            if (songlistadapter.getItemCount() == 0) {
                ArrayList<ArrayList<String>> playlist = fetchSongFromSharePreferences(sp);
                if (playlist != null) {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
                    linearLayoutManager.setStackFromEnd(true);
                    linearLayoutManager.setReverseLayout(true);

                    // get(1) means sort by title
                    Collections.sort(playlist, (o1, o2) -> o1.get(1).compareTo(o2.get(1)));

                    for (int i = 0; i < playlist.size(); i++) {
                        ArrayList<String> currSong = playlist.get(i);

                        audioFiles.add(new Song(Long.parseLong(currSong.get(0)), currSong.get(1), currSong.get(2), Long.parseLong(currSong.get(3)), currSong.get(4), currSong.get(5), Uri.parse(currSong.get(6))));

                        list.add(new SongModel());
                        list.get(list.size() - 1).setName(currSong.get(1));
                        list.get(list.size() - 1).setAuthor(currSong.get(2));
                        list.get(list.size() - 1).setDuration(Long.parseLong(currSong.get(3)));
                    }
                }
            }
        } catch (Exception e) {
            Log.e("renderer", "cannot render songList!");
        }
    }

    public static ArrayList<ArrayList<String>> fetchSongFromSharePreferences(SharedPreferences sp){
        try {
            String ssl = sp.getString("SongList", "unknown");
            Gson gson = new Gson();
            return gson.fromJson(ssl, new TypeToken<ArrayList<ArrayList<String>>>(){}.getType());
        } catch (Exception e) {
            Log.e("fetcher", "cannot fetch songList!");
        }
        return null;
    }
}
