package com.example.imnuricrestine.data.favorites;

import com.example.imnuricrestine.data.db.FavoritesDao;
import com.example.imnuricrestine.data.db.entities.Favorite;
import com.example.imnuricrestine.data.db.models.FavoriteInsert;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class FavoritesLocalDataSource implements FavoritesDataSource {
    private final FavoritesDao _favoritesDao;

    @Inject
    public FavoritesLocalDataSource(FavoritesDao favoritesDao) {
        _favoritesDao = favoritesDao;
    }

    @Override
    public CompletableFuture<ArrayList<Favorite>> getFavorites() {
        return CompletableFuture.supplyAsync(() ->
                new ArrayList<>(_favoritesDao.getFavorites())
        );
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
    public void deleteFavorite(short id) {
        System.out.println();
    }
}
