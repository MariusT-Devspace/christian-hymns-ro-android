package com.example.imnuricrestine.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.imnuricrestine.MainFragment;
import com.example.imnuricrestine.R;
import com.example.imnuricrestine.adapters.RVMainCustomAdapter;
import com.example.imnuricrestine.databinding.ActivityMainBinding;
import com.example.imnuricrestine.db.AppDatabase;
import com.example.imnuricrestine.db.entities.HymnWithLyrics;
import com.example.imnuricrestine.db.HymnsDao;
import com.example.imnuricrestine.models.Hymn;
import com.example.imnuricrestine.models.Verse;
import com.example.imnuricrestine.services.HymnsViewModel;

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
        //loadUI();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, MainFragment.class, null)
                    .commit();
        }
        /*
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(linearLayoutManager);
         */



    }



    protected void loadUI(){

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, HymnActivity.class);
        intent.putExtra(HYMN, hymns.get(position));
        //intent.putExtra(LYRICS, String.valueOf(hymnsList.get(position).lyrics));
        startActivity(intent);
    }


}