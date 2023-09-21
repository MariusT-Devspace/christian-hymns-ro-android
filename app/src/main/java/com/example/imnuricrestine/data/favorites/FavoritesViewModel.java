package com.example.imnuricrestine.data.favorites;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.imnuricrestine.data.db.entities.Favorite;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FavoritesViewModel extends AndroidViewModel {
    private final FavoritesRepository _favoritesRepository;
    private final LiveData<List<Favorite>> favorites;

    @Inject
    public FavoritesViewModel(
            Application application,
            FavoritesRepository favoritesRepository
    ) {
        super(application);
        _favoritesRepository = favoritesRepository;
        try {
            favorites = _favoritesRepository.getFavorites();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public LiveData<List<Favorite>> getFavorites() throws ExecutionException, InterruptedException { return favorites; }
    public CompletableFuture<Void> addFavorite(Favorite favorite) {
        try {
            return _favoritesRepository.addFavorite(favorite.hymn_id);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<Void> deleteFavorite(Favorite favorite) {
        try {
            return _favoritesRepository.deleteFavorite(favorite);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
