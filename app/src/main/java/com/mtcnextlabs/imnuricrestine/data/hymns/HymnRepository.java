package com.mtcnextlabs.imnuricrestine.data.hymns;

import static com.mtcnextlabs.imnuricrestine.utils.ConvertersKt.asHymn;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite;
import com.mtcnextlabs.imnuricrestine.data.db.models.HymnWithFavoriteStatus;
import com.mtcnextlabs.imnuricrestine.data.favorites.FavoriteDataSource;
import com.mtcnextlabs.imnuricrestine.di.FavoriteDataSourceModule;
import com.mtcnextlabs.imnuricrestine.models.Hymn;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

public class HymnRepository {

    private final HymnDataSource _hymnDataSource;
    private final FavoriteDataSource _favoriteLocalDataSource;


    @Inject
    public HymnRepository(
            HymnDataSource hymnDataSource,
            @FavoriteDataSourceModule.FavoriteLocalDataSourceAnnotation
            FavoriteDataSource favoriteLocalDataSource
    ) {
        _hymnDataSource = hymnDataSource;
        _favoriteLocalDataSource = favoriteLocalDataSource;
    }

    public LiveData<List<Hymn>> getHymns() throws ExecutionException, InterruptedException {
        return Transformations.map(_hymnDataSource.getHymns(), list -> {
            List<Hymn> hymns = new ArrayList<>();
            for (HymnWithFavoriteStatus item : list) {
                hymns.add(asHymn(item));
            }
            return hymns;
        });
    }

    public LiveData<Hymn> getHymnById(int hymnId) {
        return Transformations.map(_hymnDataSource.getHymnById(hymnId), hymnWithFavoriteStatus ->
            asHymn(hymnWithFavoriteStatus)
        );
    }

    public LiveData<List<Hymn>> getFavoriteHymns() throws ExecutionException, InterruptedException {
        return _hymnDataSource.getFavoriteHymns();
    }

    public CompletableFuture<Void> restoreFavorite(Favorite backup) {
        return CompletableFuture.runAsync(() -> {
            if (backup != null) {
                _favoriteLocalDataSource.insertFavorite(backup);
            }
        });
    }

    public CompletableFuture<Void> addFavorite(int hymnId) throws ExecutionException, InterruptedException {
        return CompletableFuture.runAsync(() ->
                _favoriteLocalDataSource.insertFavorite(hymnId)
        );
    }

    public CompletableFuture<Favorite> deleteFavorite(int hymnId) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            Favorite backup = _favoriteLocalDataSource.getFavoriteByHymnId(hymnId);

            if (backup != null)
                _favoriteLocalDataSource.deleteFavorite(hymnId);

            return backup;
        });
    }
}
