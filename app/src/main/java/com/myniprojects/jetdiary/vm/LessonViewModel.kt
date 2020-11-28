package com.myniprojects.jetdiary.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myniprojects.jetdiary.db.lesson.Lesson
import com.myniprojects.jetdiary.repo.LessonRepo
import com.myniprojects.jetdiary.ui.common.EditListState
import com.myniprojects.jetdiary.ui.composes.LessonRow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class LessonViewModel @ViewModelInject constructor(
    private val lessonRepo: LessonRepo
) : ViewModel()
{

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
        },
        generateNewItem = {

            val id = runBlocking {
                lessonRepo.insertLesson(Lesson())
            }

            return@EditListState getLesson(id)
        }
    )

    val lessonRow = LessonRow(lessonListState)

    fun insertLesson(lesson: Lesson)
    {
        viewModelScope.launch {
            lessonRepo.insertLesson(lesson)
        }
    }

    private fun getLesson(id: Long): Lesson = runBlocking {
        lessonRepo.getLesson(id)
    }


    init
    {
        viewModelScope.launch {
            lessonRepo.mockData()
        }
    }

}