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
import com.gorjoe.tunplmus.Utils.SongMediaStore;
import com.gorjoe.tunplmus.models.PlayListsModel;
import com.l4digital.fastscroll.FastScroller;

import java.util.ArrayList;

public class PlayListsAdapter extends RecyclerView.Adapter<PlayListsAdapter.ViewHolder> implements FastScroller.SectionIndexer {

    ArrayList<PlayListsModel> list;

    public PlayListsAdapter(ArrayList<PlayListsModel> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_playlists, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlayListsModel model = list.get(position);
        String songName = SongMediaStore.getAudioFilesArray().get(position).getTitle();
        holder.name.setText(model.getName());
        holder.total.setText(model.getTotal());

        holder.itemView.setOnClickListener(v -> {
            SnackbarUtil.makeSnackbar((Activity) holder.itemView.getContext(), "item" + position + " clicked, " + songName);
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
        TextView name, total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);
            total = itemView.findViewById(R.id.tvTotal);
        }
    }

}
