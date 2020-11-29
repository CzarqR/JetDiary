package com.myniprojects.jetdiary.repo

import com.myniprojects.jetdiary.db.student.Student
import com.myniprojects.jetdiary.db.student.StudentDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class StudentRepo @Inject constructor(
    private val studentDao: StudentDao
)
{
    val students = studentDao.getStudents()

    suspend fun mockData()
    {
        withContext(Dispatchers.IO)
        {
            studentDao.clearTable()
            studentDao.insertStudent(Student(name = "Leo", surname = "Messi"))
            studentDao.insertStudent(Student(name = "Luis", surname = "Suarez"))
            studentDao.insertStudent(Student(name = "Frankie", surname = "De Jong"))
            studentDao.insertStudent(Student(name = "Sergi", surname = "Roberto"))
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