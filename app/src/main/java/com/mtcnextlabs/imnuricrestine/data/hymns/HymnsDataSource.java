package com.mtcnextlabs.imnuricrestine.data.hymns;

import com.mtcnextlabs.imnuricrestine.data.db.HymnsDao;
import com.mtcnextlabs.imnuricrestine.data.db.models.HymnWithFavoriteStatus;

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
