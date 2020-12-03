package com.myniprojects.jetdiary.ui.studentlesson

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.myniprojects.jetdiary.R
import com.myniprojects.jetdiary.db.lesson.Lesson
import com.myniprojects.jetdiary.db.student.Student
import com.myniprojects.jetdiary.ui.common.ItemRowBase
import com.myniprojects.jetdiary.ui.studenteditor.StudentItem
import com.myniprojects.jetdiary.vm.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@Composable
fun StudentLessonBody(
    viewModel: MainViewModel,
    setTitle: (String) -> Unit,
)
{
    val lesson: Lesson? by viewModel.selectedLesson.collectAsState(null)

    lesson?.let {
        setTitle(it.name)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val list: List<Student> by viewModel.studentsInLesson.collectAsState(listOf())

        LazyColumnForIndexed(
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.item_base_margin_v),
                bottom = dimensionResource(id = R.dimen.item_base_margin_v)
            ),
            items = list
        ) { i, item ->

            ItemRowBase(
                bottomPadding = if (i == list.size - 1) dimensionResource(id = R.dimen.last_card_padding) else null
            ) {

                StudentItem(
                    student = item,
                    onCLick = {
                        viewModel.navigateToMarks(it)
                    },
                    onLongClick = {
                        // do nth
                    }
                )

            }

        }


        FloatingActionButton(
            onClick = {
                viewModel.navigateToStudentEditor()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(dimensionResource(id = R.dimen.fab_padding))

        ) {
            Icon(asset = Icons.Outlined.Add)
        }

    }
}