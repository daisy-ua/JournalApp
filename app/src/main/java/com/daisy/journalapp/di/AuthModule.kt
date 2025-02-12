package com.daisy.journalapp.di

import com.daisy.journalapp.authentication.data.repository.FirebaseAuthRepositoryImpl
import com.daisy.journalapp.authentication.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    @Binds
    @Singleton
    fun bindAuthRepository(authRepositoryImpl: FirebaseAuthRepositoryImpl): AuthRepository
}