package com.myniprojects.jetdiary.repo

import com.myniprojects.jetdiary.db.srudentlesson.LessonStudentCrossDao
import com.myniprojects.jetdiary.db.srudentlesson.LessonStudentCrossRef
import com.myniprojects.jetdiary.db.student.Student
import com.myniprojects.jetdiary.db.student.StudentDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
            studentDao.insertStudent(Student(name = "Antoine", surname = "Griezmann", 5))
            studentDao.insertStudent(Student(name = "Philippe", surname = "Coutinho", 6))
            studentDao.insertStudent(Student(name = "Ousmane", surname = "Dembélé", 7))
            studentDao.insertStudent(Student(name = "Ansu", surname = "Fati", 8))
            studentDao.insertStudent(Student(name = "Gerard", surname = "Piqué", 9))
            studentDao.insertStudent(Student(name = "Samuel", surname = "Umtiti", 10))

            studentCrossDao.clearTable()

            studentCrossDao.assignStudentToLesson(LessonStudentCrossRef(1, 1))
            studentCrossDao.assignStudentToLesson(LessonStudentCrossRef(2, 1))
            studentCrossDao.assignStudentToLesson(LessonStudentCrossRef(4, 1))
            studentCrossDao.assignStudentToLesson(LessonStudentCrossRef(6, 1))
            studentCrossDao.assignStudentToLesson(LessonStudentCrossRef(7, 1))
            studentCrossDao.assignStudentToLesson(LessonStudentCrossRef(10, 1))
        }
    }

    suspend fun assignStudentToLesson(lessonId: Long, studentId: Long) =
            withContext(Dispatchers.IO) {
                studentCrossDao.assignStudentToLesson(
                    LessonStudentCrossRef(
                        studentId = studentId,
                        lessonId = lessonId
                    )
                )
            }

    suspend fun deleteStudent(student: Student) =
            withContext(Dispatchers.IO) {
                studentDao.deleteStudent(student)
            }

    suspend fun insertStudent(student: Student) =
            withContext(Dispatchers.IO) {
                studentDao.insertStudent(student)
            }

    suspend fun updateStudent(student: Student) =
            withContext(Dispatchers.IO) {
                studentDao.updateLesson(
                    name = student.name,
                    surname = student.surname,
                    studentId = student.studentId
                )
            }

    fun getStudentsCount(): Flow<Long> = studentDao.getStudentsCount()

    suspend fun deleteStudentToLesson(lessonId: Long, studentId: Long) =
            withContext(Dispatchers.IO) {
                studentCrossDao.deleteStudentToLesson(
                    studentId = studentId,
                    lessonId = lessonId
                )
            }
}