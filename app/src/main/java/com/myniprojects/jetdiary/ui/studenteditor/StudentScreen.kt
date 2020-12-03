package com.myniprojects.jetdiary.ui.studenteditor

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.myniprojects.jetdiary.db.lesson.Lesson
import com.myniprojects.jetdiary.ui.common.DefaultEditableBody
import com.myniprojects.jetdiary.vm.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@Composable
fun StudentBody(
    viewModel: MainViewModel,
    setTitle: (String) -> Unit,
    state: LazyListState = rememberLazyListState()
)
{
    val lesson: Lesson? by viewModel.selectedLesson.collectAsState(null)

    lesson?.let {
        setTitle("${it.name} students")
    }

    DefaultEditableBody(
        editableRow = viewModel.studentEditorRow,
        state = state,
    )
}