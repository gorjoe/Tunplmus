package com.gorjoe.tunplmus.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.gorjoe.tunplmus.R;
import com.gorjoe.tunplmus.Song;
import com.gorjoe.tunplmus.SongHandler;
import com.gorjoe.tunplmus.SongListActivity;
import com.gorjoe.tunplmus.models.SongModel;

import java.util.ArrayList;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder> {

    ArrayList<SongModel> list;

    public SongListAdapter(ArrayList<SongModel> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SongModel model = list.get(position);
        String songName = SongListActivity.getAudioFilesArray().get(position).getTitle();
        holder.name.setText(model.getName());
        holder.author.setText(model.getAuthor());

        holder.itemView.setOnClickListener(v -> {
            SnackbarUtil.makeSnackbar((Activity) holder.itemView.getContext(), "item" + position + " clicked, " + songName);
            SongHandler.PlaySong(position);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, author, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvSongName);
            author = itemView.findViewById(R.id.tvAuthor);
            time = itemView.findViewById(R.id.tvTime);
        }
    }

}
