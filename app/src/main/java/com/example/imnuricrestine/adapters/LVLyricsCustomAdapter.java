package com.example.imnuricrestine.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.imnuricrestine.R;
import com.example.imnuricrestine.activities.HymnActivity;
import com.example.imnuricrestine.models.Verse;
import com.google.android.material.textview.MaterialTextView;
import java.util.ArrayList;

public class LVLyricsCustomAdapter extends BaseAdapter {

    ArrayList<Verse> lyrics;


    public LVLyricsCustomAdapter(ArrayList<Verse> lyrics){
        this.lyrics = lyrics;
    }

    @Override
    public int getCount() {
        return lyrics.size();
    }

    @Override
    public Object getItem(int position) {
        return lyrics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(container.getContext()).inflate(R.layout.lv_lyrics_item, container, false);
        }

        Verse verse = (Verse) getItem(position);

        MaterialTextView tagTextView = (MaterialTextView) convertView.findViewById(R.id.verseTagTV);
        MaterialTextView verseTextView = (MaterialTextView) convertView.findViewById(R.id.verseTextTV);

        if(!verse.isChorus()){
            HymnActivity.verseCount++;
            tagTextView.setText(String.valueOf(HymnActivity.verseCount));
        }else{
            tagTextView.setText("Ref: ");
        }



        verseTextView.setText(lyrics.get(position).getLyrics());
        return convertView;
    }
}
