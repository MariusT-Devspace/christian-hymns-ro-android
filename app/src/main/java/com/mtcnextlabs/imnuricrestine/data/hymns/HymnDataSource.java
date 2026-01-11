package com.mtcnextlabs.imnuricrestine.data.hymns;

import static com.mtcnextlabs.imnuricrestine.utils.ConvertersKt.asHymn;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.mtcnextlabs.imnuricrestine.data.db.HymnDao;
import com.mtcnextlabs.imnuricrestine.data.db.models.HymnWithFavoriteStatus;
import com.mtcnextlabs.imnuricrestine.data.db.models.HymnWithLyrics;
import com.mtcnextlabs.imnuricrestine.models.Hymn;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HymnDataSource {
    private HymnDao _hymnDao;

    @Inject
    public HymnDataSource(HymnDao hymnDao) {
        _hymnDao = hymnDao;
    }

    public LiveData<List<HymnWithFavoriteStatus>> getHymns(){
        return _hymnDao.getHymns();
    }

    public LiveData<HymnWithFavoriteStatus> getHymnById(int hymnId) {
        return _hymnDao.getHymnById(hymnId);
    }

    public LiveData<List<Hymn>> getFavoriteHymns(){
        return Transformations.map(_hymnDao.getFavoriteHymns(), list -> {
            List<Hymn> hymns = new ArrayList<>();
            for (HymnWithLyrics item : list) {
                hymns.add(asHymn(item));
            }
            return hymns;
        });
    }
}
