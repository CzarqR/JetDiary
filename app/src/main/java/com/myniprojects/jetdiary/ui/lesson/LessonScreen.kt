package com.myniprojects.jetdiary.ui.lesson

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.myniprojects.jetdiary.R
import com.myniprojects.jetdiary.ui.common.DefaultBodyy
import com.myniprojects.jetdiary.vm.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun LessonBody(
    viewModel: MainViewModel,
    setTitle: (String) -> Unit,
    state: LazyListState = rememberLazyListState()
)
{
    DefaultBodyy(
        editableRow = viewModel.lessonRow,
        fabClick = { viewModel.lessonRow.editListState.addAndEditNewItem() },
        state = state,
        setTitle = setTitle,
        title = stringResource(id = R.string.classes_name)
    )
}