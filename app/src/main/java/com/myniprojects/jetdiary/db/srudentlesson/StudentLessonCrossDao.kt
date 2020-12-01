package com.myniprojects.jetdiary.db.srudentlesson

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface StudentLessonCrossDao
{
    @Transaction
    @Query("SELECT * FROM lessons WHERE lessonId = :lessonId")
    fun getStudentsOfLesson(lessonId: Long): Flow<LessonWithStudent>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun assignStudentToLesson(studentLessonCrossRef: StudentLessonCrossRef)

//    @Transaction
//    @Query("SELECT * FROM students WHERE studentId = :studentId")
//    suspend fun getLessonsOfStudents(studentId: Long): List<StudentWithLesson>
}