package com.myniprojects.jetdiary.db.mark

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
}