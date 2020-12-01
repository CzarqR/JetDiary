package com.myniprojects.jetdiary.repo

import com.myniprojects.jetdiary.db.srudentlesson.LessonStudentCrossDao
import com.myniprojects.jetdiary.db.srudentlesson.LessonStudentCrossRef
import com.myniprojects.jetdiary.db.student.Student
import com.myniprojects.jetdiary.db.student.StudentDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class StudentRepo @Inject constructor(
    private val studentDao: StudentDao,
    private val studentCrossDao: LessonStudentCrossDao

)
{
    val students = studentDao.getStudents()

    fun getStudentsInLesson(lessonId: Long) = studentCrossDao.getStudentsOfLesson(lessonId)

    suspend fun mockData()
    {
        withContext(Dispatchers.IO)
        {
            studentDao.clearTable()
            studentDao.insertStudent(Student(name = "Luis", surname = "Suarez", 1))
            studentDao.insertStudent(Student(name = "Frankie", surname = "De Jong", 2))
            studentDao.insertStudent(Student(name = "Sergi", surname = "Roberto", 3))
            studentDao.insertStudent(Student(name = "Leo", surname = "Messi", 4))

            studentCrossDao.clearTable()
            studentCrossDao.assignStudentToLesson(LessonStudentCrossRef(1, 1))
            studentCrossDao.assignStudentToLesson(LessonStudentCrossRef(2, 1))
            studentCrossDao.assignStudentToLesson(LessonStudentCrossRef(3, 1))
        }
    }

    suspend fun deleteLesson(student: Student) =
            withContext(Dispatchers.IO) {
                studentDao.deleteStudent(student)
            }

    suspend fun insertLesson(student: Student): Long =
            withContext(Dispatchers.IO) {
                studentDao.insertStudent(student)
            }


    suspend fun clearLessonsTable() =
            withContext(Dispatchers.IO) {
                studentDao.clearTable()
            }

    suspend fun getLesson(id: Long): Student =
            withContext(Dispatchers.IO) {
                return@withContext studentDao.getStudent(id)
            }

}