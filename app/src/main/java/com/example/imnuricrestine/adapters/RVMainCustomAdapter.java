package com.example.imnuricrestine.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imnuricrestine.activities.MainActivity;
import com.example.imnuricrestine.R;
import com.example.imnuricrestine.objects.Hymn;

import java.util.ArrayList;
import java.util.List;

public class RVMainCustomAdapter extends RecyclerView.Adapter<RVMainCustomAdapter.ViewHolder> {
    private OnItemListener onItemListener;
    private List<Hymn> dataSet;
    Hymn hymn;
    private static final int textViewIndex = 1;
    private final int textViewTitle = 2;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final com.google.android.material.textview.MaterialTextView textView1;
        private final com.google.android.material.textview.MaterialTextView textView2;
        OnItemListener onItemListener;

        public ViewHolder(View view, OnItemListener onItemListener) {
            super(view);
            textView1 = view.findViewById(R.id.list_item_textView1);
            textView2 = view.findViewById(R.id.list_item_textView2);

            // Define click listener for the ViewHolder's View
            this.onItemListener = onItemListener;
            view.setOnClickListener(this);

        }

        public TextView getTextView(int textView) {
            if(textView == RVMainCustomAdapter.textViewIndex) {
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

    public RVMainCustomAdapter(ArrayList<Hymn> dataSet, OnItemListener onItemListener) {
        this.dataSet = dataSet;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public RVMainCustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_main_item, parent, false);
        return new ViewHolder(view, this.onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RVMainCustomAdapter.ViewHolder holder, int position) {
        hymn = dataSet.get(position);
        holder.getTextView(textViewIndex).setText(String.valueOf(hymn.getIndex()) + ".");
        holder.getTextView(textViewTitle).setText(hymn.getTitle());
    }

    @Override
    public int getItemCount() {
        return MainActivity.hymnsWithLyricsList.size();
    }


    public interface OnItemListener{
        void onItemClick(int position);
    }
}
