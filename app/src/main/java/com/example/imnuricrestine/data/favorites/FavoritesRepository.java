package com.example.imnuricrestine.data.favorites;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.imnuricrestine.data.db.entities.Favorite;
import com.example.imnuricrestine.di.FavoritesDataSourceModule.FavoritesLocalDataSourceAnnotation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

public class FavoritesRepository {
    private static List<Favorite> _favorites;
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

    public void addFavorite(short id) throws ExecutionException, InterruptedException {
        _favoritesLocalDataSource.addFavorite(id);
    }

    public void deleteFavorite(Favorite favorite) throws ExecutionException, InterruptedException {
        _favoritesLocalDataSource.deleteFavorite(favorite);
        Log.d("FAVORITES", "delete favorite");
    }
}
