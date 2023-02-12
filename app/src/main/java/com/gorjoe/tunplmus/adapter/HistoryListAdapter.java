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
import com.gorjoe.tunplmus.models.HistoryModel;

import java.util.ArrayList;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {

    ArrayList<HistoryModel> list;

    public HistoryListAdapter(ArrayList<HistoryModel> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryModel model = list.get(position);
        holder.name.setText(model.getName());
        holder.time.setText(model.getTime());

        holder.itemView.setOnClickListener(v -> {
            SnackbarUtil.makeSnackbar((Activity) holder.itemView.getContext(), "item" + position + " clicked");
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView time, name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.tvTime);
            name = itemView.findViewById(R.id.tvName);

        }
    }

}
