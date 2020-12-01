package com.myniprojects.jetdiary.ui.lesson

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.myniprojects.jetdiary.ui.common.DefaultBody
import com.myniprojects.jetdiary.vm.MainViewModel

@Composable
fun LessonBody(
    viewModel: MainViewModel,
    navController: NavHostController,
    state: LazyListState = rememberLazyListState()
)
{
    DefaultBody(
        editableRow = viewModel.lessonRow,
        fabClick = { viewModel.lessonRow.editListState.addAndEditNewItem() },
        state = state
    )
}