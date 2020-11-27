package com.myniprojects.jetdiary.ui.composes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.myniprojects.jetdiary.db.lesson.Lesson
import com.myniprojects.jetdiary.ui.common.EditableRow
import com.myniprojects.jetdiary.ui.common.ItemRowBase
import com.myniprojects.jetdiary.ui.theme.AppTheme
import com.myniprojects.jetdiary.ui.theme.mtypography


object LessonRow : EditableRow<Lesson>
{
    @Composable
    override fun defaultItem(
        item: Lesson,
        onClick: (Lesson) -> Unit,
        onLongClick: (Lesson) -> Unit
    )
    {
        LessonItem(
            lesson = item,
            onCLick = onClick,
            onLongClick = onLongClick
        )
    }

    @Composable
    override fun editableItem(
        item: Lesson,
        save: (Lesson) -> Unit,
        cancel: () -> Unit
    )
    {
        EditLessonItem(
            lesson = item,
            onSave = save,
            onCancel = cancel
        )
    }
}


@Composable
fun LessonItem(
    lesson: Lesson,
    onCLick: (Lesson) -> Unit,
    onLongClick: (Lesson) -> Unit
)
{
    ItemRowBase {
        Text(
            text = lesson.name,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = { onCLick(lesson) },
                    onLongClick = { onLongClick(lesson) }
                )
                .padding(8.dp),
            style = mtypography.h4,
            textAlign = TextAlign.Start,
        )
    }
}


@Composable
fun EditLessonItem(
    lesson: Lesson,
    onSave: (Lesson) -> Unit,
    onCancel: () -> Unit
)
{
    val (text, setText) = remember { mutableStateOf(lesson.name) }

    ItemRowBase {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = setText
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LessonItemPreview()
{
    AppTheme {
        LessonItem(Lesson("Mobile programing"), {}, {})
    }
}


@Preview(showBackground = true)
@Composable
fun EditLessonItemPreview()
{
    AppTheme {
        EditLessonItem(Lesson("Mobile programing"), {}, {})
    }
}






