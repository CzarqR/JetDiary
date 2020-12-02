package com.myniprojects.jetdiary.ui.mark

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.myniprojects.jetdiary.R
import com.myniprojects.jetdiary.ui.common.DefaultBody
import com.myniprojects.jetdiary.vm.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun MarksBody(
    viewModel: MainViewModel,
    setTitle: (String) -> Unit,
    state: LazyListState = rememberLazyListState()
)
{
    DefaultBody(
        editableRow = viewModel.markRow,
        fabClick = {
            viewModel.markRow.editListState.addAndEditNewItem()
        },
        state = state,
        setTitle = setTitle,
        title = stringResource(id = R.string.marks)
    )
}