package com.mtcnextlabs.imnuricrestine.data.hymns;

import static com.mtcnextlabs.imnuricrestine.utils.ConvertersKt.asHymn;

import com.mtcnextlabs.imnuricrestine.data.db.models.HymnWithFavoriteStatus;
import com.mtcnextlabs.imnuricrestine.models.Hymn;
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
