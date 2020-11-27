package com.myniprojects.jetdiary.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myniprojects.jetdiary.repo.LessonRepo
import com.myniprojects.jetdiary.utils.EditListState
import kotlinx.coroutines.launch
import timber.log.Timber

class LessonViewModel @ViewModelInject constructor(
    private val lessonRepo: LessonRepo
) : ViewModel()
{
//    val lessons = lessonRepo.lessons

    val lessonListState = EditListState(
        flowList = lessonRepo.lessons,
        onSave = {
            Timber.d("Save $it")
        },
        onDelete = {
            Timber.d("Delete $it")
        },
        clickItem = {
            Timber.d("Item clicked $it")
        }
    )

    init
    {
        viewModelScope.launch {
            lessonRepo.mockData()
        }
    }

}