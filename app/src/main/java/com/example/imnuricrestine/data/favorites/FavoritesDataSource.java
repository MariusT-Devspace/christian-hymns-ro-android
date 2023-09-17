package com.example.imnuricrestine.data.favorites;

import androidx.lifecycle.LiveData;

import com.example.imnuricrestine.data.db.entities.Favorite;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FavoritesDataSource {
    //CompletableFuture<ArrayList<Favorite>> getFavorites();
    LiveData<List<Favorite>> getFavorites();
    CompletableFuture<Void> addFavorite(short id);
    CompletableFuture<Void> deleteFavorite(Favorite favorite);
}
