package com.example.imnuricrestine.data.favorites;

import com.example.imnuricrestine.data.db.entities.Favorite;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class FavoritesRemoteDataSource implements FavoritesDataSource {
    @Inject
    public FavoritesRemoteDataSource() {

    }

    @Override
    public CompletableFuture<ArrayList<Favorite>> getFavorites() {
        return null;
    }

    @Override
    public void addFavorite(short id) {

    }

    @Override
    public void deleteFavorite(short id) {

    }
}
