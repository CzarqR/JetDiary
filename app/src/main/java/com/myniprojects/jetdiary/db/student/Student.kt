package com.myniprojects.jetdiary.db.student

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    val name: String = "",
    val surname: String = "",
    @PrimaryKey(autoGenerate = true) val id: Long = 0L
)