package com.myniprojects.jetdiary.ui.composes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.myniprojects.jetdiary.R
import com.myniprojects.jetdiary.ui.common.EditableList
import com.myniprojects.jetdiary.vm.LessonViewModel

@Composable
fun LessonScreen(lessonViewModel: LessonViewModel)
{


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.select_classes)) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        },
        floatingActionButton = {

            FloatingActionButton(onClick = {
                lessonViewModel.lessonRow.editListState.addAndEditNewItem()
            }) {
                Icon(asset = Icons.Outlined.Add)
            }

        },
        bodyContent = {
            LessonBody(
                viewModel = lessonViewModel
            )
        }
    )
}

@Composable
fun LessonBody(
    viewModel: LessonViewModel
)
{
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        EditableList(
            editableRow = viewModel.lessonRow,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}
