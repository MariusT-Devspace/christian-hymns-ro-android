package com.example.imnuricrestine.di;

import static com.example.imnuricrestine.utils.ConstantsKt.DATABASE_NAME;

import android.content.Context;

import androidx.room.Room;

import com.example.imnuricrestine.data.db.AppDatabase;
import com.example.imnuricrestine.data.db.FavoritesDao;
import com.example.imnuricrestine.data.db.HymnsDao;

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
