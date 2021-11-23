package com.example.imnuricrestine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<Hymns> dataSet;
    Hymns hymn;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final com.google.android.material.textview.MaterialTextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (com.google.android.material.textview.MaterialTextView) view.findViewById(R.id.list_item_textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public CustomAdapter(List<Hymns> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_text_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        hymn = dataSet.get(position);
        holder.getTextView().setText(hymn.title);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
