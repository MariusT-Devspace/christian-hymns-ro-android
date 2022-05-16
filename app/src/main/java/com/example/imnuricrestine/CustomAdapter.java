package com.example.imnuricrestine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private OnItemListener onItemListener;
    private List<Hymns> dataSet;
    Hymns hymn;
    private static final int textViewIndex = 1;
    private final int textViewTitle = 2;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final com.google.android.material.textview.MaterialTextView textView1;
        private final com.google.android.material.textview.MaterialTextView textView2;
        OnItemListener onItemListener;

        public ViewHolder(View view, OnItemListener onItemListener) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView1 = view.findViewById(R.id.list_item_textView1);
            textView2 = view.findViewById(R.id.list_item_textView2);

            this.onItemListener = onItemListener;
            view.setOnClickListener(this);
        }

        public TextView getTextView(int textView) {
            if(textView == CustomAdapter.textViewIndex) {
                return textView1;
            }else{
                return textView2;
            }
        }

        @Override
        public void onClick(View v) {
            this.onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public CustomAdapter(List<Hymns> dataSet, OnItemListener onItemListener) {
        this.dataSet = dataSet;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_text_item, parent, false);
        return new ViewHolder(view, this.onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        hymn = dataSet.get(position);
        holder.getTextView(textViewIndex).setText(String.valueOf(hymn.index) + ".");
        holder.getTextView(textViewTitle).setText(hymn.title);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public interface OnItemListener{
        void onItemClick(int position);
    }
}
