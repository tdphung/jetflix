package com.littlewind.myjetflix.inject

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.littlewind.jetflix.data.api.MovieApi
import com.littlewind.jetflix.domain.repository.LanguageDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.themoviedb.org/3/"

@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {

    @Provides
    @Singleton
    fun provideMovieApi(
        retrofit: Retrofit
    ) = retrofit.create(MovieApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    @IntoSet
    fun provideLanguageInterceptor(languageDataStore: LanguageDataStore): Interceptor {
        return LanguageInterceptor(languageDataStore)
    }

    @Provides
    @Singleton
    fun provideOkClient(
        interceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
//        okHttpClientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//        okHttpClientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
//        okHttpClientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
//        okHttpClientBuilder.dns(object : Dns {
//            override fun lookup(hostname: String) =
//                InetAddress.getAllByName(hostname).toList()
//        })
        okHttpClientBuilder.interceptors().addAll(interceptors)
        okHttpClientBuilder.addInterceptor(
            Interceptor { chain ->
                val request = chain.request()

                // TODO extract this to other Interceptor
                val url = request.url.newBuilder()
                    .addQueryParameter("api_key", "9487082a53af88e1866c341355155846").build()
                val newRequest = request.newBuilder().url(url).build()

                chain.proceed(newRequest)
            }
        )

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(loggingInterceptor)

        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .serializeNulls()
            .create()
    }
}
