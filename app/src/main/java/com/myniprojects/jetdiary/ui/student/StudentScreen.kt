package com.myniprojects.jetdiary.ui.student

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.myniprojects.jetdiary.ui.common.EditableList
import com.myniprojects.jetdiary.vm.MainViewModel

@Composable
fun StudentBody(
    viewModel: MainViewModel,
    navController: NavHostController,
    state: LazyListState = rememberLazyListState()
)
{
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        EditableList(
            editableRow = viewModel.studentRow,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = state
        )
    }
}