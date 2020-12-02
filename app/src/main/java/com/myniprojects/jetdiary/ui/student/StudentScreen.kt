package com.myniprojects.jetdiary.ui.student

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.myniprojects.jetdiary.R
import com.myniprojects.jetdiary.ui.common.DefaultBody
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
    DefaultBody(
        editableRow = viewModel.studentRow,
        fabClick = {
            Timber.d("Click fab")
            viewModel.studentRow.editListState.addAndEditNewItem()
        },
        state = state,
        setTitle = setTitle,
        title = stringResource(id = R.string.students)
    )
}