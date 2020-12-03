package com.myniprojects.jetdiary.db.lesson

import androidx.room.*
import com.myniprojects.jetdiary.db.mark.MarkAssigned
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao
{
    @Query("SELECT * FROM lessons ORDER BY name ASC")
    fun getLessons(): Flow<List<Lesson>>

    @Query("DELETE FROM lessons")
    suspend fun clearTable()

    @Query("SELECT COUNT(lessonId) FROM lessons")
    fun getLessonCount(): Flow<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lesson: Lesson)

    @Query("UPDATE lessons SET name=:lessonName WHERE lessonId=:lessonId")
    suspend fun updateLesson(lessonName: String, lessonId: Long)

    @Delete
    suspend fun deleteLesson(lesson: Lesson)
}