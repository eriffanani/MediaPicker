package com.erif.mediapicker.ui.audio.list;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erif.mediapicker.model.ItemMedia;
import com.erif.mediapicker.callback.ItemClickListener;
import com.erif.mediapicker.callback.ItemLongClickListener;
import com.erif.singleclick.PreventDoubleClick;

import java.util.ArrayList;
import java.util.List;

public class AdapterAudioPicker extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemMedia> list = new ArrayList<>();
    private ItemClickListener clickListener = null;
    private ItemLongClickListener longClickListener = null;

    public AdapterAudioPicker() {}
    public AdapterAudioPicker(List<ItemMedia> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemMedia item = list.get(position);
        if (holder instanceof Holder) {
            ((Holder) holder).bind();
            holder.itemView.setOnClickListener(v -> new PreventDoubleClick(() -> {
                if (clickListener != null) {
                    clickListener.onClick(item);
                }
            }));

            holder.itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    longClickListener.onLongClick(item);
                }
                return false;
            });
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<ItemMedia> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void update(ItemMedia item) {
        int position = list.indexOf(item);
        if (position >= 0) {
            notifyItemChanged(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClickListener(ItemClickListener listener) {
        this.clickListener = listener;
    }

    public void setOnLongClickListener(ItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    // Holder Class
    private static class Holder extends RecyclerView.ViewHolder {

        public Holder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind() {

        }
    }

}
