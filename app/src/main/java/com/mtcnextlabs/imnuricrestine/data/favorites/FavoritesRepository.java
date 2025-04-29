package com.mtcnextlabs.imnuricrestine.data.favorites;


import android.util.Log;

import androidx.lifecycle.LiveData;

import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite;
import com.mtcnextlabs.imnuricrestine.di.FavoritesDataSourceModule.FavoritesLocalDataSourceAnnotation;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

public class FavoritesRepository {
    private final FavoritesDataSource _favoritesLocalDataSource;

    @Inject
    public FavoritesRepository(
            @FavoritesLocalDataSourceAnnotation FavoritesDataSource favoritesLocalDataSource
    ) {
        _favoritesLocalDataSource = favoritesLocalDataSource;
    }

    public LiveData<List<Favorite>> getFavorites() throws ExecutionException, InterruptedException {
        return _favoritesLocalDataSource.getFavorites();
    }

    public CompletableFuture<Void> addFavorite(short id) throws ExecutionException, InterruptedException {
        return _favoritesLocalDataSource.addFavorite(id);
    }

    public CompletableFuture<Void> deleteFavorite(Favorite favorite) throws ExecutionException, InterruptedException {
        Log.d("FAVORITES", "delete favorite");
        return _favoritesLocalDataSource.deleteFavorite(favorite);
    }
}
