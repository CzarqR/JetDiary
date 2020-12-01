package com.myniprojects.jetdiary.db.srudentlesson

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    primaryKeys = [
        "studentId",
        "lessonId"
    ],
    tableName = "student_lesson_cross"
)
data class LessonStudentCrossRef(
    val studentId: Long,
    val lessonId: Long,
)
