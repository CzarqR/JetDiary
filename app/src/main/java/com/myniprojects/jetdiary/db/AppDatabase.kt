package com.myniprojects.jetdiary.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.myniprojects.jetdiary.db.lesson.Lesson
import com.myniprojects.jetdiary.db.lesson.LessonDao
import com.myniprojects.jetdiary.db.mark.MarkAssigned
import com.myniprojects.jetdiary.db.mark.MarkConverter
import com.myniprojects.jetdiary.db.mark.MarkDao
import com.myniprojects.jetdiary.db.srudentlesson.LessonStudentCrossDao
import com.myniprojects.jetdiary.db.srudentlesson.LessonStudentCrossRef
import com.myniprojects.jetdiary.db.student.Student
import com.myniprojects.jetdiary.db.student.StudentDao

@Database(
    entities = [
        Lesson::class,
        Student::class,
        LessonStudentCrossRef::class,
        MarkAssigned::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(MarkConverter::class)
abstract class AppDatabase : RoomDatabase()
{
    abstract val lessonDao: LessonDao
    abstract val studentDao: StudentDao
    abstract val lessonStudentCrossRefDao: LessonStudentCrossDao
    abstract val markDao: MarkDao
}