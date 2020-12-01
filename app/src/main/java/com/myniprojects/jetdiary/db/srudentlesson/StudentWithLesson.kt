package com.myniprojects.jetdiary.db.srudentlesson

import androidx.room.*
import com.myniprojects.jetdiary.db.lesson.Lesson
import com.myniprojects.jetdiary.db.student.Student

data class StudentWithLesson(
    @Embedded val student: Student,
    @Relation(
        parentColumn = "studentId",
        entityColumn = "lessonId",
        associateBy = Junction(StudentLessonCrossRef::class)
    )
    val lessons: List<Lesson>
)