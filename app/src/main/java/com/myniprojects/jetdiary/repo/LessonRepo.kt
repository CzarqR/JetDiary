package com.myniprojects.jetdiary.repo

import com.myniprojects.jetdiary.db.AppDatabase
import com.myniprojects.jetdiary.db.lesson.Lesson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LessonRepo @Inject constructor(
    private val database: AppDatabase
)
{
    val lessons = database.lessonDao.getLessons()

    suspend fun mockData()
    {
        withContext(Dispatchers.IO)
        {
            database.lessonDao.clearTable()

            database.lessonDao.insertLesson(Lesson("Mobile programing"))
            database.lessonDao.insertLesson(Lesson("Database SQL"))
            database.lessonDao.insertLesson(Lesson("Programing intro"))
            database.lessonDao.insertLesson(Lesson("Mathematica"))
        }
    }

    suspend fun deleteLesson(lesson: Lesson) =
            withContext(Dispatchers.IO) {
                database.lessonDao.deleteLesson(lesson)
            }

    suspend fun insertLesson(lesson: Lesson): Long =
            withContext(Dispatchers.IO) {
                database.lessonDao.insertLesson(lesson)
            }


    suspend fun clearLessonsTable() =
            withContext(Dispatchers.IO) {
                database.lessonDao.clearTable()
            }

    suspend fun getLesson(id: Long): Lesson =
            withContext(Dispatchers.IO) {
                return@withContext database.lessonDao.getLesson(id)
            }

}