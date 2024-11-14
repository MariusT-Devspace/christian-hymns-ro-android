package com.example.imnuricrestine.data.hymns;

import static com.example.imnuricrestine.utils.ConvertersKt.asHymn;

import com.example.imnuricrestine.data.db.models.HymnWithFavoriteStatus;
import com.example.imnuricrestine.models.Hymn;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

public class HymnsRepository {

    private static List<HymnWithFavoriteStatus> _hymnsWithFavoriteStatus;
    private ArrayList<Hymn> _hymns;
    private final HymnsDataSource _hymnsDataSource;

    @Inject
    public HymnsRepository(HymnsDataSource hymnsDataSource) {
        _hymnsDataSource = hymnsDataSource;
    }

    private void populateHymns() {
        _hymns = new ArrayList<>();
        for(var hymnWithFavoriteStatus : _hymnsWithFavoriteStatus) {
            _hymns.add(asHymn(hymnWithFavoriteStatus));
        }
    }

    public ArrayList<Hymn> getHymns() throws ExecutionException, InterruptedException {
        _hymnsWithFavoriteStatus = _hymnsDataSource.getHymns.get();
        populateHymns();
        return _hymns;
    }
}
