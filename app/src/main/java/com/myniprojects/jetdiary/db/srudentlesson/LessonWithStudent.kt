package com.myniprojects.jetdiary.db.srudentlesson

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.myniprojects.jetdiary.db.lesson.Lesson
import com.myniprojects.jetdiary.db.student.Student

data class LessonWithStudent(
    @Embedded val lesson: Lesson,
    @Relation(
        parentColumn = "lessonId",
        entityColumn = "studentId",
        associateBy = Junction(StudentLessonCrossRef::class)
    )
    val students: List<Student>
)