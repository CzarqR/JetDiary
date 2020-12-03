package com.myniprojects.jetdiary.repo

import com.myniprojects.jetdiary.db.lesson.Lesson
import com.myniprojects.jetdiary.db.lesson.LessonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

            lessonDao.insertLesson(Lesson("Database SQL", 1))
            lessonDao.insertLesson(Lesson("Programing intro", 2))
            lessonDao.insertLesson(Lesson("Mathematica", 3))
            lessonDao.insertLesson(Lesson("Mobile programing", 4))

        }
    }

    suspend fun deleteLesson(lesson: Lesson) =
            withContext(Dispatchers.IO) {
                lessonDao.deleteLesson(lesson)
            }

    suspend fun insertLesson(lesson: Lesson) =
            withContext(Dispatchers.IO) {
                lessonDao.insertLesson(lesson)
            }

    suspend fun clearLessonsTable() =
            withContext(Dispatchers.IO) {
                lessonDao.clearTable()
            }

    suspend fun updateLesson(lesson: Lesson) =
            withContext(Dispatchers.IO) {
                lessonDao.updateLesson(
                    lessonName = lesson.name,
                    lessonId = lesson.lessonId
                )
            }

    fun getLessonsCount(): Flow<Long> = lessonDao.getLessonCount()

}