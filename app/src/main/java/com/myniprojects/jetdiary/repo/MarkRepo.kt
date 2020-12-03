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
                    note = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution",
                    date = Date(System.currentTimeMillis())
                )
            )

            markDao.insertMark(
                MarkAssigned(
                    studentId = 1,
                    lessonId = 1, Mark.THREE_HALF,
                    note = "Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).",
                    date = Date(System.currentTimeMillis())
                )
            )

            markDao.insertMark(
                MarkAssigned(
                    studentId = 1,
                    lessonId = 1, Mark.FIVE,
                    note = "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable.",
                    date = Date(System.currentTimeMillis())
                )
            )

            markDao.insertMark(
                MarkAssigned(
                    studentId = 1,
                    lessonId = 1, Mark.TWO,
                    note = "If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text.",
                    date = Date(System.currentTimeMillis())
                )
            )

            markDao.insertMark(
                MarkAssigned(
                    studentId = 1,
                    lessonId = 1, Mark.THREE,
                    note = "All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,",
                    date = Date(System.currentTimeMillis())
                )
            )

            markDao.insertMark(
                MarkAssigned(
                    studentId = 1,
                    lessonId = 1, Mark.FOUR_HALF,
                    note = "Some short note",
                    date = Date(System.currentTimeMillis() - 86_400_000)
                )
            )

            markDao.insertMark(
                MarkAssigned(
                    studentId = 1,
                    lessonId = 1, Mark.FOUR_HALF,
                    note = "",
                    date = Date(System.currentTimeMillis() - 86_400_000)
                )
            )

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