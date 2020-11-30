package com.myniprojects.jetdiary.ui.lesson

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import com.myniprojects.jetdiary.R
import com.myniprojects.jetdiary.ui.common.EditableList
import com.myniprojects.jetdiary.vm.MainViewModel
import timber.log.Timber

@Composable
fun LessonBody(
    viewModel: MainViewModel,
    navController: NavHostController,
    state: LazyListState = rememberLazyListState()
)
{
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        EditableList(
            editableRow = viewModel.lessonRow,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            state = state
        )

        FloatingActionButton(
            onClick = {
                Timber.d("Button click")
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(dimensionResource(id = R.dimen.fab_padding))

        ) {
            Icon(asset = Icons.Outlined.Add)
        }

    }
}
