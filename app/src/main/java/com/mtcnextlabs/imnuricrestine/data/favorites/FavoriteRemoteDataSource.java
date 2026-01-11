package com.mtcnextlabs.imnuricrestine.data.favorites;

import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite;

import javax.inject.Inject;

public class FavoriteRemoteDataSource implements FavoriteDataSource {
    @Inject
    public FavoriteRemoteDataSource() {}

    @Override
    public Favorite getFavoriteByHymnId(int hymnId) { return null; }

    @Override
    public void insertFavorite(int hymnId) {}

    @Override
    public void insertFavorite(Favorite favorite) {}

    @Override
    public void deleteFavorite(int hymnId) {}
}
