package com.example.imnuricrestine.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.imnuricrestine.R;
import com.example.imnuricrestine.adapters.LVLyricsCustomAdapter;
import com.example.imnuricrestine.databinding.ActivityHymnBinding;
import com.example.imnuricrestine.objects.Hymn;
import com.google.android.material.textview.MaterialTextView;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

public class HymnActivity extends SwipeBackActivity {
    ActivityHymnBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hymn);
        setDragDirectMode(SwipeBackLayout.DragDirectMode.VERTICAL);
        Hymn hymn = (Hymn) getIntent().getParcelableExtra(MainActivity.HYMN);

        MaterialTextView titleTextView = (MaterialTextView) findViewById(R.id.titleTextView);
        titleTextView.setText(hymn.getTitle());

        LVLyricsCustomAdapter customAdapter = new LVLyricsCustomAdapter(hymn.getLyrics());
        binding = ActivityHymnBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ListView listView = binding.lyricsLV;
        listView.setAdapter(customAdapter);
    }
}