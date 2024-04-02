package com.bonsai_software.icebonsai.architecture.di

import android.content.Context
import androidx.room.Room
import com.bonsai_software.icebonsai.architecture.ShoppingDao
import com.bonsai_software.icebonsai.architecture.ShoppingDetailsDao
import com.bonsai_software.icebonsai.database.IceBonsaiDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideShoppingDao(iceBonsaiDatabase: IceBonsaiDatabase): ShoppingDao {
        return iceBonsaiDatabase.shoppingDao()
    }
    @Provides
    fun provideShoppingDetailsDao(iceBonsaiDatabase: IceBonsaiDatabase): ShoppingDetailsDao {
        return iceBonsaiDatabase.shoppingDetailsDao()
    }
    @Provides
    @Singleton
    fun provideIceBonsaiDatabase(@ApplicationContext appContext: Context): IceBonsaiDatabase {
        return Room.databaseBuilder(appContext, IceBonsaiDatabase::class.java, "IceBonsaiDatabase")
            .build()
    }
}