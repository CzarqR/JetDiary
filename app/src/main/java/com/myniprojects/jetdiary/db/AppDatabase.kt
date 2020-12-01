package com.myniprojects.jetdiary.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myniprojects.jetdiary.db.lesson.Lesson
import com.myniprojects.jetdiary.db.lesson.LessonDao
import com.myniprojects.jetdiary.db.srudentlesson.StudentLessonCrossDao
import com.myniprojects.jetdiary.db.srudentlesson.StudentLessonCrossRef
import com.myniprojects.jetdiary.db.student.Student
import com.myniprojects.jetdiary.db.student.StudentDao

@Database(
    entities = [
        Lesson::class,
        Student::class,
        StudentLessonCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase()
{
    abstract val lessonDao: LessonDao
    abstract val studentDao: StudentDao
    abstract val studentLessonCrossRefDao: StudentLessonCrossDao
}