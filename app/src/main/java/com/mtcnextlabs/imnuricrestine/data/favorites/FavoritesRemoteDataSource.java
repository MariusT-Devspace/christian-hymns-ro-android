package com.mtcnextlabs.imnuricrestine.data.favorites;

import androidx.lifecycle.LiveData;

import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class FavoritesRemoteDataSource implements FavoritesDataSource {
    @Inject
    public FavoritesRemoteDataSource() {

    }


    @Override
    public LiveData<List<Favorite>> getFavorites() {
        return null;
    }

    @Override
    public CompletableFuture<Void> addFavorite(short id) {
        return null;
    }

    @Override
    public CompletableFuture<Void> deleteFavorite(Favorite favorite) { return null; }
}
