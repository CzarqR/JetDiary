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
    suspend fun insertStudent(student: Student): Long

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("SELECT * FROM students WHERE studentId=:id")
    suspend fun getStudent(id: Long): Student
}