package com.littlewind.myjetflix.inject

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.littlewind.jetflix.data.repository.LanguageDataStoreImpl
import com.littlewind.jetflix.domain.repository.LanguageDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAppDataStore(@ApplicationContext context: Context, gson: Gson): LanguageDataStore {
        return LanguageDataStoreImpl(gson, context.preferencesDataStore)
    }
}
