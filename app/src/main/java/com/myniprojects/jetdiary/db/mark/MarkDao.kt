package com.myniprojects.jetdiary.db.mark

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMark(markAssigned: MarkAssigned)

    @Query("SELECT * FROM mark_assigned WHERE lessonId=:lessonId AND studentId=:studentId")
    fun getMarks(studentId: Long, lessonId: Long): Flow<List<MarkAssigned>>

    @Query("DELETE FROM mark_assigned")
    suspend fun clearTable()

    @Query("UPDATE mark_assigned SET note=:note, mark=:mark WHERE assignedMarkId=:assignedMarkId")
    suspend fun updateMark(note: String, mark: Mark, assignedMarkId: Long)

    @Delete
    suspend fun deleteMark(markAssigned: MarkAssigned)
}