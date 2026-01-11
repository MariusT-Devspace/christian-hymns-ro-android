package com.mtcnextlabs.imnuricrestine.data.favorites;

import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite;

public interface FavoriteDataSource {
    Favorite getFavoriteByHymnId(int hymnId);
    void insertFavorite(int hymnId);
    void insertFavorite(Favorite favorite);
    void deleteFavorite(int hymnId);
}
