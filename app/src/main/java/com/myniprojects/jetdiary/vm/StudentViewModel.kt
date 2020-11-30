package com.myniprojects.jetdiary.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myniprojects.jetdiary.db.student.Student
import com.myniprojects.jetdiary.repo.StudentRepo
import com.myniprojects.jetdiary.ui.common.EditListState
import com.myniprojects.jetdiary.ui.student.StudentRow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class StudentViewModel @ViewModelInject constructor(
    private val studentRepo: StudentRepo
) : ViewModel()
{
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


    init
    {
        viewModelScope.launch {
            studentRepo.mockData()
        }
    }

}