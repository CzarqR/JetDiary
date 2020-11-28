package com.myniprojects.jetdiary.di

import android.content.Context
import androidx.room.Room
import com.myniprojects.jetdiary.db.AppDatabase
import com.myniprojects.jetdiary.db.lesson.LessonDao
import com.myniprojects.jetdiary.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule
{
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()


    @Singleton
    @Provides
    fun provideNewsDao(db: AppDatabase): LessonDao = db.lessonDao
}