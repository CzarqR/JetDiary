package com.myniprojects.jetdiary.repo

import com.myniprojects.jetdiary.db.mark.Mark
import com.myniprojects.jetdiary.db.mark.MarkAssigned
import com.myniprojects.jetdiary.db.mark.MarkDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class MarkRepo @Inject constructor(
    private val markDao: MarkDao
)
{
    fun getMarks(lessonId: Long, studentId: Long) = markDao.getMarks(studentId, lessonId)
    fun getAllMarks() = markDao.getAllMarks()

    suspend fun mockData()
    {
        withContext(Dispatchers.IO)
        {
            markDao.clearTable()

            markDao.insertMark(
                MarkAssigned(
                    studentId = 1,
                    lessonId = 1, Mark.FOUR,
                    note = "Some note",
                    date = Date(System.currentTimeMillis())
                )
            )
            markDao.insertMark(MarkAssigned(1, 1, Mark.FIVE))
            markDao.insertMark(MarkAssigned(1, 1, Mark.TWO))
            markDao.insertMark(MarkAssigned(1, 1, Mark.THREE_HALF))
        }
    }

    suspend fun clearTable() =
            withContext(Dispatchers.IO) {
                markDao.clearTable()
            }


    suspend fun insertMark(markAssigned: MarkAssigned) =
            withContext(Dispatchers.IO) {
                markDao.insertMark(markAssigned)
            }


    suspend fun deleteMark(markAssigned: MarkAssigned) =
            withContext(Dispatchers.IO) {
                markDao.deleteMark(markAssigned)
            }

    suspend fun updateMark(markAssigned: MarkAssigned) =
            withContext(Dispatchers.IO) {
                markDao.updateMark(
                    note = markAssigned.note,
                    mark = markAssigned.mark,
                    assignedMarkId = markAssigned.assignedMarkId
                )
            }

    fun getMarksCount(): Flow<Long> = markDao.getMarksCount()


}