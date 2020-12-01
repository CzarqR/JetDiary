package com.myniprojects.jetdiary.db.srudentlesson

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface LessonStudentCrossDao
{
    @Transaction
    @Query("SELECT * FROM lessons WHERE lessonId = :lessonId")
    fun getStudentsOfLesson(lessonId: Long): Flow<LessonWithStudent>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun assignStudentToLesson(lessonStudentCrossRef: LessonStudentCrossRef)

    @Query("DELETE FROM student_lesson_cross")
    suspend fun clearTable()

}