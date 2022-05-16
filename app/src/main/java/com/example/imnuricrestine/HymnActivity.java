package com.example.imnuricrestine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.imnuricrestine.databinding.ActivityHymnBinding;
import com.google.android.material.textview.MaterialTextView;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

public class HymnActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hymn);
        setDragDirectMode(SwipeBackLayout.DragDirectMode.VERTICAL);
        String title = getIntent().getStringExtra(MainActivity.TITLE);
        String lyrics = getIntent().getStringExtra(MainActivity.LYRICS);
        MaterialTextView titleTextView = (MaterialTextView) findViewById(R.id.titleTextView);
        MaterialTextView lyricsTextView = (MaterialTextView) findViewById(R.id.lyricsTextView);
        titleTextView.setText(title);
        lyricsTextView.setText(lyrics);

    }
}