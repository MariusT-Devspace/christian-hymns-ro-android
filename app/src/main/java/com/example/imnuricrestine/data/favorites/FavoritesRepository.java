package com.example.imnuricrestine.data.favorites;


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

    public ArrayList<Favorite> getFavorites() throws ExecutionException, InterruptedException {
        return new ArrayList<>(_favoritesLocalDataSource.getFavorites().get());
    }

    public void addFavorite(short id) throws ExecutionException, InterruptedException {
        _favoritesLocalDataSource.addFavorite(id);
    }
}
