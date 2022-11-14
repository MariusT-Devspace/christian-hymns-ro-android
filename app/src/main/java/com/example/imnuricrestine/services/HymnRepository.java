package com.example.imnuricrestine.services;

import android.app.Application;

import androidx.room.Room;

import com.example.imnuricrestine.db.AppDatabase;
import com.example.imnuricrestine.db.HymnsDao;
import com.example.imnuricrestine.db.entities.HymnWithLyrics;
import com.example.imnuricrestine.models.Hymn;
import com.example.imnuricrestine.models.Verse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class HymnRepository {
    private static List<HymnWithLyrics> hymnsWithLyricsList;
    private ArrayList<Hymn> hymns;
    private static final String HYMN = "com.example.imnuricrestine.HYMN";
    private String CHORUS_TAG = "Ref:";

    Application application;
    HymnRepository(Application application){
        this.application = application;
    }

    private void populateHymns() {
        hymns = new ArrayList<>();
        for(var hymnWithLyrics : hymnsWithLyricsList){
            ArrayList<Verse> verses = new ArrayList<>();
            int verseTagCount = 0;
            for(var verse : hymnWithLyrics.lyrics){
                String tag = "";
                if (verse.is_chorus)
                    tag = CHORUS_TAG;
                else
                    tag = String.valueOf(++verseTagCount);
                verses.add(new Verse(verse.verse_text, tag));
            }
            hymns.add(new Hymn(hymnWithLyrics.hymn.hymn_index, hymnWithLyrics.hymn.title, verses));
        }
    }

    private CompletableFuture<Void> loadDataBase = CompletableFuture.runAsync(() -> {
        // Load database and recyclerview
            AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "hymns.db")
                    .createFromAsset("Hymnsdb")
                    .fallbackToDestructiveMigration()
                    .build();
            HymnsDao hymnsDao = db.hymnsDao();
            hymnsWithLyricsList = hymnsDao.getAll();
            db.close();
    }
    );
    public ArrayList<Hymn> getHymns(Application application) throws ExecutionException, InterruptedException {
        loadDataBase.get();
        populateHymns();
        return hymns;

    }
}
