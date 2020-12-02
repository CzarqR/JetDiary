package com.myniprojects.jetdiary.ui.studenteditor

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.myniprojects.jetdiary.R
import com.myniprojects.jetdiary.ui.common.DefaultEditableBody
import com.myniprojects.jetdiary.vm.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@Composable
fun StudentBody(
    viewModel: MainViewModel,
    setTitle: (String) -> Unit,
    state: LazyListState = rememberLazyListState()
)
{
    DefaultEditableBody(
        editableRow = viewModel.studentRow,
        state = state,
        setTitle = setTitle,
        title = stringResource(id = R.string.students)
    )
}