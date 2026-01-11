package com.mtcnextlabs.imnuricrestine.di;


import com.mtcnextlabs.imnuricrestine.data.favorites.FavoriteDataSource;
import com.mtcnextlabs.imnuricrestine.data.favorites.FavoriteLocalDataSource;
import com.mtcnextlabs.imnuricrestine.data.favorites.FavoriteRemoteDataSource;

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
public abstract class FavoriteDataSourceModule {

    // Qualifiers
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FavoriteLocalDataSourceAnnotation {}

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FavoriteRemoteDataSourceAnnotation {}

    // Bindings
    @FavoriteLocalDataSourceAnnotation
    @Binds
    @Singleton
    public abstract FavoriteDataSource bindFavoriteLocalDataSource(
            FavoriteLocalDataSource favoriteLocalDataSource
    );

    @FavoriteRemoteDataSourceAnnotation
    @Binds
    @Singleton
    public abstract FavoriteDataSource bindFavoriteRemoteDataSource(
            FavoriteRemoteDataSource favoriteRemoteDataSource
    );

}
