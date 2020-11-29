package com.myniprojects.jetdiary.ui.student

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.myniprojects.jetdiary.R
import com.myniprojects.jetdiary.ui.common.EditableList
import com.myniprojects.jetdiary.vm.StudentViewModel

@Composable
fun StudentScreen(studentViewModel: StudentViewModel)
{
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.students)) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        },
        floatingActionButton = {

            FloatingActionButton(onClick = {
                // todo set state to position 0
            }) {
                Icon(asset = Icons.Outlined.Add)
            }

        },
        bodyContent = {
            EditableList(
                editableRow = studentViewModel.studentRow,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    )
}