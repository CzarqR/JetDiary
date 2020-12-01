package com.myniprojects.jetdiary.db.lesson

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class Lesson(
    val name: String = "",
    @PrimaryKey(autoGenerate = true) val lessonId: Long = 0
)
