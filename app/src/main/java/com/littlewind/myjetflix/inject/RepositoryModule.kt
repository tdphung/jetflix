package com.littlewind.myjetflix.inject

import com.littlewind.jetflix.data.repository.ConfigRepositoryImpl
import com.littlewind.jetflix.data.repository.MovieRepositoryImpl
import com.littlewind.jetflix.domain.repository.ConfigRepository
import com.littlewind.jetflix.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindConfigRepository(repository: ConfigRepositoryImpl): ConfigRepository
}
