package com.mtcnextlabs.imnuricrestine.data.hymns;

import static com.mtcnextlabs.imnuricrestine.data.MappersKt.asHymn;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.mtcnextlabs.imnuricrestine.data.db.entities.FavoriteEntity;
import com.mtcnextlabs.imnuricrestine.data.db.models.HymnWithFavorite;
import com.mtcnextlabs.imnuricrestine.data.favorites.FavoriteDataSource;
import com.mtcnextlabs.imnuricrestine.di.FavoriteDataSourceModule;
import com.mtcnextlabs.imnuricrestine.models.Hymn;
import com.mtcnextlabs.imnuricrestine.models.HymnDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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

    public LiveData<List<Hymn>> getHymns() {
        return Transformations.map(_hymnDataSource.getHymns(), items -> {
            List<Hymn> hymns = new ArrayList<>();
            for (HymnWithFavorite item : items)
                hymns.add(asHymn(item));
            return hymns;
        });
    }

    public LiveData<HymnDetail> getHymnById(int hymnId) {
        return Transformations.map(
            _hymnDataSource.getHymnById(hymnId), hymnDetailWithFavorite ->
            asHymn(hymnDetailWithFavorite)
        );
    }

    public LiveData<List<Hymn>> getFavoriteHymns() {
        return Transformations.map(
            _hymnDataSource.getFavoriteHymns(), items -> {
                List<Hymn> hymns = new ArrayList<>();
                for (HymnWithFavorite hymnWithFavorite : items)
                    hymns.add(asHymn(hymnWithFavorite));
                return hymns;
            }
        );
    }

    public CompletableFuture<Void> restoreFavorite(FavoriteEntity backup) {
        return CompletableFuture.runAsync(() -> {
            if (backup != null) {
                _favoriteLocalDataSource.insertFavorite(backup);
            }
        });
    }

    public CompletableFuture<Void> addFavorite(int hymnId) {
        return CompletableFuture.runAsync(() ->
                _favoriteLocalDataSource.insertFavorite(hymnId)
        );
    }

    public CompletableFuture<FavoriteEntity> deleteFavorite(int hymnId) {
        return CompletableFuture.supplyAsync(() -> {
            FavoriteEntity backup = _favoriteLocalDataSource.getFavoriteByHymnId(hymnId);

            if (backup != null)
                _favoriteLocalDataSource.deleteFavorite(hymnId);

            return backup;
        });
    }
}
