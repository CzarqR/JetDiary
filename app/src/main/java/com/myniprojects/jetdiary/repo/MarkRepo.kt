package com.myniprojects.jetdiary.repo

import com.myniprojects.jetdiary.db.mark.Mark
import com.myniprojects.jetdiary.db.mark.MarkAssigned
import com.myniprojects.jetdiary.db.mark.MarkDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarkRepo @Inject constructor(
    private val markDao: MarkDao
)
{
    fun getMarks(lessonId: Long, studentId: Long) = markDao.getMarks(studentId, lessonId)


    suspend fun mockData()
    {
        withContext(Dispatchers.IO)
        {
            markDao.clearTable()

            markDao.insertMark(MarkAssigned(1, 1, Mark.FOUR, "Some note"))
            markDao.insertMark(MarkAssigned(1, 1, Mark.FIVE))
            markDao.insertMark(MarkAssigned(1, 1, Mark.TWO))
            markDao.insertMark(MarkAssigned(1, 1, Mark.THREE_HALF))
        }
    }


    suspend fun clearTable() =
            withContext(Dispatchers.IO) {
                markDao.clearTable()
            }


    suspend fun insertMark(markAssigned: MarkAssigned): Long =
            withContext(Dispatchers.IO) {
                markDao.insertMark(markAssigned)
            }


    suspend fun deleteMark(markAssigned: MarkAssigned) =
            withContext(Dispatchers.IO) {
                markDao.deleteMark(markAssigned)
            }


    suspend fun getMark(id: Long): MarkAssigned =
            withContext(Dispatchers.IO) {
                return@withContext markDao.getMark(id)
            }

}