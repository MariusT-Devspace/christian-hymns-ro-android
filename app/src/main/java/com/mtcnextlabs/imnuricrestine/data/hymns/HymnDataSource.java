package com.mtcnextlabs.imnuricrestine.data.hymns;


import static com.mtcnextlabs.imnuricrestine.data.MappersKt.asHymn;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.mtcnextlabs.imnuricrestine.data.db.HymnDao;
import com.mtcnextlabs.imnuricrestine.data.db.models.HymnDetailWithFavorite;
import com.mtcnextlabs.imnuricrestine.data.db.models.HymnWithFavorite;
import com.mtcnextlabs.imnuricrestine.models.HymnDetail;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HymnDataSource {
    private HymnDao _hymnDao;

    @Inject
    public HymnDataSource(HymnDao hymnDao) {
        _hymnDao = hymnDao;
    }

    public LiveData<List<HymnWithFavorite>> getHymns(){
        return _hymnDao.getHymns();
    }

    public LiveData<HymnDetailWithFavorite> getHymnById(int hymnId) {
        return _hymnDao.getHymnById(hymnId);
    }

    public LiveData<List<HymnWithFavorite>> getFavoriteHymns(){
        return _hymnDao.getFavoriteHymns();
    }
}
