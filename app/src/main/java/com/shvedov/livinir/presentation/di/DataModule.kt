package com.shvedov.livinir.presentation.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.shvedov.livinir.data.mapper.PostNetToPostDbMapper
import com.shvedov.livinir.data.mapper.UserNetToUserMapper
import com.shvedov.livinir.data.network.api.PostApi
import com.shvedov.livinir.data.network.api.UserApi
import com.shvedov.livinir.data.repository.PostRepository
import com.shvedov.livinir.data.repository.PostRepositoryImpl
import com.shvedov.livinir.data.repository.UserRepository
import com.shvedov.livinir.data.repository.UserRepositoryImpl
import com.shvedov.livinir.presentation.entity.User
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provdieGsonFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    @Named("host")
    fun provideHost(): String = "http://192.168.1.185/"

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory, @Named("host") host: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(host)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun providePostApi(retrofit: Retrofit): PostApi = retrofit.create(PostApi::class.java)

    @Provides
    @Singleton
    fun providePostRepository(api: PostApi, mapperNetToDb: PostNetToPostDbMapper): PostRepository =
        PostRepositoryImpl(api, mapperNetToDb)

    @Provides
    @Singleton
    fun provideUserRepository(api: UserApi, mapperNet: UserNetToUserMapper): UserRepository =
        UserRepositoryImpl(api, mapperNet)
}