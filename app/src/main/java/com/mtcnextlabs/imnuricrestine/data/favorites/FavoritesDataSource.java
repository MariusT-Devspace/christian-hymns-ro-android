package com.mtcnextlabs.imnuricrestine.data.favorites;

import androidx.lifecycle.LiveData;

import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FavoritesDataSource {
    LiveData<List<Favorite>> getFavorites();
    CompletableFuture<Void> addFavorite(int hymnId);
    CompletableFuture<Void> addFavorite(Favorite favorite);
    CompletableFuture<Void> deleteFavorite(Favorite favorite);
}
