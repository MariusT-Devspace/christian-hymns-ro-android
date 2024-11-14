package com.example.imnuricrestine.data.hymns;

import com.example.imnuricrestine.data.db.HymnsDao;
import com.example.imnuricrestine.data.db.models.HymnWithFavoriteStatus;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class HymnsDataSource {
    private HymnsDao _hymnsDao;

    @Inject
    public HymnsDataSource(HymnsDao hymnsDao) {
        _hymnsDao = hymnsDao;
    }

    public final CompletableFuture<List<HymnWithFavoriteStatus>> getHymns =
        CompletableFuture.supplyAsync(() ->
            _hymnsDao.getHymns()
    );
}
