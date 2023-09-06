package com.example.imnuricrestine.data.hymns;

import com.example.imnuricrestine.data.db.HymnsDao;
import com.example.imnuricrestine.data.db.entities.HymnWithLyrics;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class HymnsDataSource {
    private HymnsDao _hymnsDao;

    @Inject
    public HymnsDataSource(HymnsDao hymnsDao) {
        _hymnsDao = hymnsDao;
    }

    public final CompletableFuture<List<HymnWithLyrics>> getHymns = CompletableFuture.supplyAsync(() ->
            _hymnsDao.getHymns()
    );
}
