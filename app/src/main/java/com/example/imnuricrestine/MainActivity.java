package com.example.imnuricrestine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.imnuricrestine.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomAdapter.OnItemListener{
    ActivityMainBinding binding;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager linearLayoutManager;
    List<Hymns> hymnsList;
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
            hymnsList = hymnsDao.getAll();
            CustomAdapter customAdapter = new CustomAdapter(hymnsList, this);
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
        intent.putExtra(TITLE, String.valueOf(hymnsList.get(position).title));
        intent.putExtra(LYRICS, String.valueOf(hymnsList.get(position).lyrics));
        startActivity(intent);
    }
}