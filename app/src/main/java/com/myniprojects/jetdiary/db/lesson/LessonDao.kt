package com.myniprojects.jetdiary.db.lesson

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao
{
    @Query("SELECT * FROM lessons ORDER BY name ASC")
    fun getLessons(): Flow<List<Lesson>>

    @Query("DELETE FROM lessons")
    suspend fun clearTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lesson: Lesson): Long

    @Query("UPDATE lessons SET name=:lessonName WHERE lessonId=:lessonId")
    suspend fun updateLesson(lessonName: String, lessonId: Long)

    @Delete
    suspend fun deleteLesson(lesson: Lesson)

    @Query("SELECT * FROM lessons WHERE lessonId=:id")
    suspend fun getLesson(id: Long): Lesson
}