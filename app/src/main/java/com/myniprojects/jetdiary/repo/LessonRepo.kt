package com.myniprojects.jetdiary.repo

import com.myniprojects.jetdiary.db.lesson.Lesson
import com.myniprojects.jetdiary.db.lesson.LessonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LessonRepo @Inject constructor(
    private val lessonDao: LessonDao
)
{
    val lessons = lessonDao.getLessons()

    suspend fun mockData()
    {
        withContext(Dispatchers.IO)
        {
            lessonDao.clearTable()

            lessonDao.insertLesson(Lesson("Mobile programing"))
            lessonDao.insertLesson(Lesson("Database SQL"))
            lessonDao.insertLesson(Lesson("Programing intro"))
            lessonDao.insertLesson(Lesson("Mathematica"))
        }
    }

    suspend fun deleteLesson(lesson: Lesson) =
            withContext(Dispatchers.IO) {
                lessonDao.deleteLesson(lesson)
            }

    suspend fun insertLesson(lesson: Lesson): Long =
            withContext(Dispatchers.IO) {
                lessonDao.insertLesson(lesson)
            }


    suspend fun clearLessonsTable() =
            withContext(Dispatchers.IO) {
                lessonDao.clearTable()
            }

    suspend fun getLesson(id: Long): Lesson =
            withContext(Dispatchers.IO) {
                return@withContext lessonDao.getLesson(id)
            }

}