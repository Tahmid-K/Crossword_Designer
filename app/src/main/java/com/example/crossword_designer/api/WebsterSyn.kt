package com.example.crossword_designer.api



import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val API_KEY  = "b43b4dc1-8f25-4e4d-a3db-0e024afac87d"
const val BASE_URL = "https://www.dictionaryapi.com/api/v3/"

interface WebsterSyn {
    @GET("references/thesaurus/json/{word}")
    fun getSynonyms(@Path("word") aWord : String) : Deferred<Webster>

    companion object {
        operator fun invoke() : WebsterSyn {
            val theInterceptor = Interceptor {
                    chain ->
                val url = chain.request().url().newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()
                val theRequest = chain.request().newBuilder().url(url)
                    .build()
                return@Interceptor chain.proceed(theRequest)
            }
            val theOkHttpClient = OkHttpClient.Builder()
                .addInterceptor(theInterceptor)
                .build()

            return Retrofit
                .Builder()
                .client(theOkHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebsterSyn::class.java)
        }
    }
}