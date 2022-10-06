package com.littlewind.myjetflix.inject

import com.littlewind.jetflix.domain.repository.LanguageDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class LanguageInterceptor(private val languageDataStore: LanguageDataStore) : Interceptor {
    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        // TODO get languageCode App Config first, if null -> get from LanguageDataStore
        val languageCode = runBlocking { languageDataStore.currentLanguageCode() }
        val url = request.url.newBuilder().addQueryParameter(LANGUAGE, languageCode).build()
        val newRequest = request.newBuilder().url(url).build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val LANGUAGE = "language"
    }
}
