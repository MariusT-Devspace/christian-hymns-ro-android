package com.example.imnuricrestine.data.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.imnuricrestine.data.db.FavoritesDao;
import com.example.imnuricrestine.data.db.entities.Favorite;
import com.example.imnuricrestine.data.db.models.FavoriteInsert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class FavoritesLocalDataSource implements FavoritesDataSource {
    private final FavoritesDao _favoritesDao;

    @Inject
    public FavoritesLocalDataSource(FavoritesDao favoritesDao) {
        _favoritesDao = favoritesDao;
    }



    @Override
    public LiveData<List<Favorite>> getFavorites() {
        return _favoritesDao.getFavorites();
    }

    @Override
    public CompletableFuture<Void> addFavorite(short id) {
        return CompletableFuture.runAsync(() -> {
            _favoritesDao.insertFavorite(
                    new FavoriteInsert(id)
            );
        });
    }

    @Override
    public CompletableFuture<Void> deleteFavorite(Favorite favorite) {
        return CompletableFuture.runAsync(() -> {
            _favoritesDao.deleteFavorite(favorite);
        });
    }
}
