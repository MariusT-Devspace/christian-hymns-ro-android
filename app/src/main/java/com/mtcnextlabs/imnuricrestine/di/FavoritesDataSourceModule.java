package com.mtcnextlabs.imnuricrestine.di;


import com.mtcnextlabs.imnuricrestine.data.favorites.FavoritesDataSource;
import com.mtcnextlabs.imnuricrestine.data.favorites.FavoritesLocalDataSource;
import com.mtcnextlabs.imnuricrestine.data.favorites.FavoritesRemoteDataSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class FavoritesDataSourceModule {

    // Qualifiers
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FavoritesLocalDataSourceAnnotation {}

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FavoritesRemoteDataSourceAnnotation {}

    // Bindings
    @FavoritesLocalDataSourceAnnotation
    @Binds
    @Singleton
    public abstract FavoritesDataSource bindFavoritesLocalDataSource(
            FavoritesLocalDataSource favoritesLocalDataSource
    );

    @FavoritesRemoteDataSourceAnnotation
    @Binds
    @Singleton
    public abstract FavoritesDataSource bindFavoritesRemoteDataSource(
            FavoritesRemoteDataSource favoritesRemoteDataSource
    );

}
