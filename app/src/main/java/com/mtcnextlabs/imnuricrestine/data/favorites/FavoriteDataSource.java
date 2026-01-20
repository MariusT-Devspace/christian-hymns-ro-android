package com.mtcnextlabs.imnuricrestine.data.favorites;

import com.mtcnextlabs.imnuricrestine.data.db.entities.FavoriteEntity;

public interface FavoriteDataSource {
    FavoriteEntity getFavoriteByHymnId(int hymnId);
    void insertFavorite(int hymnId);
    void insertFavorite(FavoriteEntity favoriteEntity);
    void deleteFavorite(int hymnId);
}
