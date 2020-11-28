package com.myniprojects.jetdiary.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myniprojects.jetdiary.repo.LessonRepo
import com.myniprojects.jetdiary.ui.common.EditListState
import com.myniprojects.jetdiary.ui.composes.LessonRow
import kotlinx.coroutines.launch
import timber.log.Timber

class LessonViewModel @ViewModelInject constructor(
    private val lessonRepo: LessonRepo
) : ViewModel()
{

    private val lessonListState = EditListState(
        flowList = lessonRepo.lessons,
        onSave = {
            viewModelScope.launch {
                lessonRepo.insertLesson(it)
            }
        },
        onDelete = {
            viewModelScope.launch {
                lessonRepo.deleteLesson(it)
            }
        },
        clickItem = {
            Timber.d("Item clicked $it")
        }
    )

    val lessonRow = LessonRow(lessonListState)

    init
    {
        viewModelScope.launch {
            lessonRepo.mockData()
        }
    }

}