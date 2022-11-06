package com.example.imnuricrestine.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.imnuricrestine.adapters.RVMainCustomAdapter;
import com.example.imnuricrestine.databinding.ActivityMainBinding;
import com.example.imnuricrestine.db.AppDatabase;
import com.example.imnuricrestine.db.entities.HymnWithLyrics;
import com.example.imnuricrestine.db.HymnsDao;
import com.example.imnuricrestine.models.Hymn;
import com.example.imnuricrestine.models.Verse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RVMainCustomAdapter.OnItemListener{
    ActivityMainBinding binding;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager linearLayoutManager;
    public static List<HymnWithLyrics> hymnsWithLyricsList;
    public ArrayList<Hymn> hymns;
    public static final String HYMN = "com.example.imnuricrestine.HYMN";
    private String CHORUS_TAG = "Ref:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadUI();

        // Load database and recyclerview
        Thread thread = new Thread(() -> {
            AppDatabase db = Room.databaseBuilder(getBaseContext(), AppDatabase.class, "hymns.db")
                    .createFromAsset("Hymnsdb")
                    .fallbackToDestructiveMigration()
                    .build();
            HymnsDao hymnsDao = db.hymnsDao();
            //hymnsMap = hymnsDao.getAll();
            hymnsWithLyricsList = hymnsDao.getAll();
            populateHymns();
            //RVMainCustomAdapter customAdapter = new RVMainCustomAdapter(List.copyOf(hymnsMap.keySet()), this);
            RVMainCustomAdapter customAdapter = new RVMainCustomAdapter(hymns,this);
            runOnUiThread(() -> recyclerView.setAdapter(customAdapter));
            db.close();
        });

        thread.start();

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

    protected void loadUI(){
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, HymnActivity.class);
        intent.putExtra(HYMN, hymns.get(position));
        //intent.putExtra(LYRICS, String.valueOf(hymnsList.get(position).lyrics));
        startActivity(intent);
    }


}