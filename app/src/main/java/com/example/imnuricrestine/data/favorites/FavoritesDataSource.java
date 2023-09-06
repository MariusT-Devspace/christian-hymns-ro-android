package com.example.imnuricrestine.data.favorites;

import com.example.imnuricrestine.data.db.entities.Favorite;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface FavoritesDataSource {
    CompletableFuture<ArrayList<Favorite>> getFavorites();
    CompletableFuture<Void> addFavorite(short id);
    void deleteFavorite(short id);
}
