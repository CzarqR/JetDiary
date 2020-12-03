package com.myniprojects.jetdiary.db.mark

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.myniprojects.jetdiary.db.lesson.Lesson
import com.myniprojects.jetdiary.db.student.Student
import java.util.*

@Entity(
    tableName = "mark_assigned",
    foreignKeys = [
        ForeignKey(
            entity = Student::class,
            parentColumns = ["studentId"],
            childColumns = ["studentId"],
            onDelete = CASCADE,
        ),
        ForeignKey(
            entity = Lesson::class,
            parentColumns = ["lessonId"],
            childColumns = ["lessonId"],
            onDelete = CASCADE,
        )
    ]
)
data class MarkAssigned(
    val studentId: Long,
    val lessonId: Long,
    val mark: Mark,
    val note: String = "",
    val date: Date = Date(0),
    @PrimaryKey(autoGenerate = true) val assignedMarkId: Long = 0L
)
