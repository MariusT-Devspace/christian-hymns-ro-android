package com.mtcnextlabs.imnuricrestine.data.hymns;

import static com.mtcnextlabs.imnuricrestine.utils.ConvertersKt.asHymn;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.mtcnextlabs.imnuricrestine.data.db.models.HymnWithFavoriteStatus;
import com.mtcnextlabs.imnuricrestine.models.Hymn;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

public class HymnsRepository {

    private final HymnsDataSource _hymnsDataSource;

    @Inject
    public HymnsRepository(HymnsDataSource hymnsDataSource) {
        _hymnsDataSource = hymnsDataSource;
    }

    public List<Hymn> getHymns() throws ExecutionException, InterruptedException {
        List<Hymn> hymns = new ArrayList<>();
        _hymnsDataSource.getHymns
                .thenAccept(hymnsWithFavoriteStatus -> {
                    for (HymnWithFavoriteStatus item : hymnsWithFavoriteStatus) {
                        hymns.add(asHymn(item));
                    }
                });
        return hymns;
    }

    public LiveData<List<Hymn>> getHymnsAsync() throws ExecutionException, InterruptedException {
        return Transformations.map(_hymnsDataSource.getHymnsAsync(), list -> {
            List<Hymn> hymns = new ArrayList<>();
            for (HymnWithFavoriteStatus item : list) {
                hymns.add(asHymn(item));
            }
            return hymns;
        });
    }
}
