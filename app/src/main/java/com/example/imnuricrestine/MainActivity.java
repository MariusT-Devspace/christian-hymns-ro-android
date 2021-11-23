package com.example.imnuricrestine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;

import com.example.imnuricrestine.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager linearLayoutManager;
    List<Hymns> hymnsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView = (RecyclerView) binding.recyclerView;
        recyclerView.setLayoutManager(linearLayoutManager);

        Thread thread = new Thread(() -> {
            AppDatabase db = Room.databaseBuilder(getBaseContext(), AppDatabase.class, "hymns.db")
                    .createFromAsset("Hymnsdb")
                    .fallbackToDestructiveMigration()
                    .build();
            HymnsDao hymnsDao = db.hymnsDao();
            hymnsList = hymnsDao.getAll();
            CustomAdapter customAdapter = new CustomAdapter(hymnsList);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    recyclerView.setAdapter(customAdapter);

                }
            });
        });

        thread.start();


    }
}