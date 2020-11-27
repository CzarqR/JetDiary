package com.myniprojects.jetdiary.db.lesson

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao
{
    @Query("SELECT * FROM lessons ORDER BY name DESC")
    fun getLessons(): Flow<List<Lesson>>

    @Query("DELETE FROM lessons")
    suspend fun clearTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lesson: Lesson)


}