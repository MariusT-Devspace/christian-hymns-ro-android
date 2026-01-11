package com.mtcnextlabs.imnuricrestine.data.favorites;

import com.mtcnextlabs.imnuricrestine.data.db.FavoriteDao;
import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite;
import com.mtcnextlabs.imnuricrestine.data.db.models.FavoriteInsert;

import javax.inject.Inject;

public class FavoriteLocalDataSource implements FavoriteDataSource {
    private final FavoriteDao _favoriteDao;

    @Inject
    public FavoriteLocalDataSource(FavoriteDao favoriteDao) {
        _favoriteDao = favoriteDao;
    }

    @Override
    public Favorite getFavoriteByHymnId(int hymnId) {
        return _favoriteDao.getFavoriteByHymnId(hymnId);
    }

    @Override
    public void insertFavorite(int hymnId) {
        _favoriteDao.insertFavorite(
                new FavoriteInsert(hymnId)
        );
    }

    @Override
    public void insertFavorite(Favorite favorite) {
        _favoriteDao.insertFavoriteWithId(
                favorite
        );
    }


    @Override
    public void deleteFavorite(int hymnId) {
        _favoriteDao.deleteFavorite(hymnId);
    }
}
