package com.myniprojects.jetdiary.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myniprojects.jetdiary.db.lesson.Lesson
import com.myniprojects.jetdiary.db.lesson.LessonDao

@Database(
    entities = [
        Lesson::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase()
{
    abstract val lessonDao: LessonDao
}