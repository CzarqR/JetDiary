package com.myniprojects.jetdiary.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myniprojects.jetdiary.db.lesson.Lesson
import com.myniprojects.jetdiary.db.student.Student
import com.myniprojects.jetdiary.repo.LessonRepo
import com.myniprojects.jetdiary.repo.StudentRepo
import com.myniprojects.jetdiary.ui.common.EditListState
import com.myniprojects.jetdiary.ui.lesson.LessonRow
import com.myniprojects.jetdiary.ui.student.StudentRow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class MainViewModel @ViewModelInject constructor(
    private val lessonRepo: LessonRepo,
    private val studentRepo: StudentRepo,
) : ViewModel()
{
    init
    {
        viewModelScope.launch {
            lessonRepo.mockData()
            studentRepo.mockData()
        }
    }

    // region lesson

    lateinit var selectedLesson: Lesson
        private set

    private val lessonListState = EditListState(
        flowList = lessonRepo.lessons,
        onSave = {
            insertLesson(it)
        },
        onDelete = {
            viewModelScope.launch {
                lessonRepo.deleteLesson(it)
            }
        },
        clickItem = {
            Timber.d("Item clicked $it")
            selectedLesson = it
            _navigateToStudents.value = true
        },
        generateNewItem = {

            val id = runBlocking {
                lessonRepo.insertLesson(Lesson())
            }

            return@EditListState getLesson(id)
        },
        {
            return@EditListState it.copy(
                name = it.name.trim()
            )
        }
    )

    private val _navigateToStudents: MutableLiveData<Boolean> = MutableLiveData()
    val navigateToStudents: LiveData<Boolean>
        get() = _navigateToStudents

    fun studentsNavigated()
    {
        _navigateToStudents.value = false
    }

    val lessonRow = LessonRow(lessonListState)

    private fun insertLesson(lesson: Lesson)
    {
        viewModelScope.launch {
            lessonRepo.insertLesson(lesson)
        }
    }

    private fun getLesson(id: Long): Lesson = runBlocking {
        lessonRepo.getLesson(id)
    }

    // endregion

    // region students

    private val studentListState = EditListState(
        flowList = studentRepo.students,
        onSave = {
            insertStudent(it)
        },
        onDelete = {
            viewModelScope.launch {
                studentRepo.deleteLesson(it)
            }
        },
        clickItem = {
            Timber.d("Item clicked $it")
        },
        generateNewItem = {

            val id = runBlocking {
                studentRepo.insertLesson(Student())
            }

            return@EditListState getStudent(id)
        },
        {
            return@EditListState it.copy(
                name = it.name.trim(),
                surname = it.surname.trim()
            )
        }
    )


    val studentRow = StudentRow(studentListState)

    private fun insertStudent(student: Student)
    {
        viewModelScope.launch {
            studentRepo.insertLesson(student)
        }
    }

    private fun getStudent(id: Long): Student = runBlocking {
        studentRepo.getLesson(id)
    }

    // endregion

}