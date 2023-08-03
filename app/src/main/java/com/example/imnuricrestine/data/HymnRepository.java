package com.example.imnuricrestine.data;

import android.app.Application;

import androidx.room.Room;

import com.example.imnuricrestine.data.db.AppDatabase;
import com.example.imnuricrestine.data.db.HymnsDao;
import com.example.imnuricrestine.data.db.entities.Favorites;
import com.example.imnuricrestine.data.db.entities.HymnWithLyrics;
import com.example.imnuricrestine.data.models.Hymn;
import com.example.imnuricrestine.data.models.Verse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class HymnRepository {
    private static List<HymnWithLyrics> _hymnsWithLyricsList;
    private static List<Favorites> _favorites;

    private ArrayList<Hymn> _hymns;

    Application application;
    HymnRepository(Application application){
        this.application = application;
    }

    private void populateHymns() {
        _hymns = new ArrayList<>();
        _favorites = new ArrayList<>();

        for(var hymnWithLyrics : _hymnsWithLyricsList) {
            ArrayList<Verse> verses = new ArrayList<>();
            int verseTagCount = 0;
            for(var verse : hymnWithLyrics.lyrics){
                String tag = "";
                String CHORUS_TAG = "Ref";
                if (verse.is_chorus)
                    tag = CHORUS_TAG;
                else
                    tag = String.valueOf(++verseTagCount);
                verses.add(new Verse(verse.verse_text, tag));
            }
            _hymns.add(new Hymn(hymnWithLyrics.hymn.hymn_index, hymnWithLyrics.hymn.title, verses));
        }
    }



    private CompletableFuture<Void> loadDataBase = CompletableFuture.runAsync(() -> {
        // Load database and recyclerview
            AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "hymns.db")
                    .createFromAsset("Hymnsdb")
                    .fallbackToDestructiveMigration()
                    .build();
            HymnsDao hymnsDao = db.hymnsDao();
            _hymnsWithLyricsList = hymnsDao.getAll();
            _favorites = hymnsDao.getFavorites();
            db.close();
    }
    );
    public ArrayList<Hymn> getHymns(Application application) throws ExecutionException, InterruptedException {
        loadDataBase.get();
        populateHymns();
        return _hymns;
    }

    public List<Favorites> getFavorites(Application application) throws ExecutionException, InterruptedException {
        return _favorites;
    }
}
