package com.mtcnextlabs.imnuricrestine.di;

import static com.mtcnextlabs.imnuricrestine.utils.ConstantsKt.DATABASE_NAME;

import android.content.Context;

import androidx.room.Room;

import com.mtcnextlabs.imnuricrestine.data.db.AppDatabase;
import com.mtcnextlabs.imnuricrestine.data.db.FavoritesDao;
import com.mtcnextlabs.imnuricrestine.data.db.HymnsDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public static AppDatabase provideAppDatabase(@ApplicationContext Context appContext) {
        return Room.databaseBuilder(appContext, AppDatabase.class, "hymns.db")
                .createFromAsset(DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    public static HymnsDao provideHymnsDao(AppDatabase db) {
        return db.hymnsDao();
    }

    @Provides
    public static FavoritesDao provideFavoritesDao(AppDatabase db) {
        return db.favoritesDao();
    }
}
