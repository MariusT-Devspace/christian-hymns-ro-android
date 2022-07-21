package com.example.imnuricrestine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.imnuricrestine.databinding.ActivityMainBinding;
import com.example.imnuricrestine.db.AppDatabase;
import com.example.imnuricrestine.db.entities.Hymn;
import com.example.imnuricrestine.db.entities.HymnWithLyrics;
import com.example.imnuricrestine.db.HymnsDao;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CustomAdapter.OnItemListener{
    ActivityMainBinding binding;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager linearLayoutManager;
    Map<Hymn, HymnWithLyrics> hymnsMap;
    public static final String TITLE = "com.example.imnuricrestine.TITLE";
    public static final String LYRICS = "com.example.imnuricrestine.LYRICS";

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
            hymnsMap = hymnsDao.getAll();
            CustomAdapter customAdapter = new CustomAdapter(List.copyOf(hymnsMap.keySet()), this);
            runOnUiThread(() -> recyclerView.setAdapter(customAdapter));
            db.close();
        });

        thread.start();



        
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
        intent.putExtra(TITLE, String.valueOf(hymnsMap.get("Hymn")));
        //intent.putExtra(LYRICS, String.valueOf(hymnsList.get(position).lyrics));
        startActivity(intent);
    }


}