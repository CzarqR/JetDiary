package com.myniprojects.jetdiary.ui.mark

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.myniprojects.jetdiary.db.student.Student
import com.myniprojects.jetdiary.ui.common.DefaultEditableBody
import com.myniprojects.jetdiary.vm.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@Composable
fun MarksBody(
    viewModel: MainViewModel,
    setTitle: (String) -> Unit,
    state: LazyListState = rememberLazyListState()
)
{
    val student: Student? by viewModel.selectedStudent.collectAsState(null)

    student?.let {
        setTitle("${it.surname} ${it.name}")
    }


    DefaultEditableBody(
        editableRow = viewModel.markRow,
        state = state
    )
}