package com.myniprojects.jetdiary.db.srudentlesson

import androidx.room.Entity

@Entity(
    primaryKeys = [
        "studentId",
        "lessonId"
    ]
)
data class StudentLessonCrossRef(
    val studentId: Long,
    val lessonId: Long
)
