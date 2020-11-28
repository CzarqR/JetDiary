package com.myniprojects.jetdiary.ui.composes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.myniprojects.jetdiary.R
import com.myniprojects.jetdiary.ui.common.EditableList
import com.myniprojects.jetdiary.vm.LessonViewModel
import timber.log.Timber

@Composable
fun LessonScreen(lessonViewModel: LessonViewModel)
{

    val (addingState, setAddingState) = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.select_classes)) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                setAddingState(!addingState)
                Timber.d("Button clicked, current state $addingState")
            }) {
                Icon(asset = Icons.Outlined.Add)
            }
        },
        bodyContent = {
            LessonBody(
                lessonRow = lessonViewModel.lessonRow,
                addingState = addingState
            )
        }
    )
}

@Composable
fun LessonBody(
    lessonRow: LessonRow,
    addingState: Boolean
)
{
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        EditableList(
            editableRow = lessonRow,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        if (addingState)
        {
            AddLesson()
        }
    }
}

@Composable
fun AddLesson()
{
    Button(onClick = {}) {
        Text(text = "Add")
    }
}