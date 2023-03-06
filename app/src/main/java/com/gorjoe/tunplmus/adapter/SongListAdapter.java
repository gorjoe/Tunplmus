package com.gorjoe.tunplmus.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.gorjoe.tunplmus.MediaPlayerActivity;
import com.gorjoe.tunplmus.R;
import com.gorjoe.tunplmus.Utils.SongHandler;
import com.gorjoe.tunplmus.SongListActivity;
import com.gorjoe.tunplmus.Utils.SongMediaStore;
import com.gorjoe.tunplmus.models.SongModel;
import com.l4digital.fastscroll.FastScroller;

import java.util.ArrayList;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder> implements FastScroller.SectionIndexer {

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
        String songName = SongMediaStore.getAudioFilesArray().get(position).getTitle();
        holder.name.setText(model.getName());
        holder.author.setText(model.getAuthor());
        holder.time.setText(SongHandler.convertToMMSS(model.getDuration()));

        holder.itemView.setOnClickListener(v -> {
            SnackbarUtil.makeSnackbar((Activity) holder.itemView.getContext(), "item" + position + " clicked, " + songName);
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, MediaPlayerActivity.class);
            context.startActivity(intent);
            Activity activity = (Activity) context;
            activity.overridePendingTransition( R.anim.slide_in_up, 0 );
            LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
            View view = inflater.inflate(R.layout.activity_media_player, null);
            SeekBar sb = view.findViewById(R.id.seekBar);
            SongHandler songHand = new SongHandler(sb);
            songHand.PlaySong(position);
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public String getSectionText(int position) {
        return SongMediaStore.getAudioFilesArray().get(position).getTitle().substring(0, 1);
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
