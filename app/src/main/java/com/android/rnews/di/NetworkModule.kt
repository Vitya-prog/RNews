package com.android.rnews.di

import android.content.Context
import com.android.rnews.NetworkConnection
import com.android.rnews.data.RedditApiService
import com.android.rnews.data.RedditPagingSource
import com.android.rnews.data.RedditRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    fun provideNetworkConnection(@ApplicationContext context: Context):NetworkConnection{
        return NetworkConnection(context)
    }
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl("https://www.reddit.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    fun provideRedditApiService(retrofit: Retrofit): RedditApiService =
        retrofit.create(RedditApiService::class.java)

    @Provides
    fun provideRedditRepository(redditApiService: RedditApiService): RedditRepository =
        RedditRepository(RedditPagingSource(redditApiService))
}