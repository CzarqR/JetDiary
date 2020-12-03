package com.myniprojects.jetdiary.db.student

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao
{
    @Query("SELECT * FROM students ORDER BY name ASC")
    fun getStudents(): Flow<List<Student>>

    @Query("DELETE FROM students")
    suspend fun clearTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Query("UPDATE students SET name=:name, surname=:surname WHERE studentId=:studentId")
    suspend fun updateLesson(name: String, surname: String, studentId: Long)

    @Query("SELECT COUNT(studentId) FROM students")
    fun getStudentsCount(): Flow<Long>

    @Delete
    suspend fun deleteStudent(student: Student)

}