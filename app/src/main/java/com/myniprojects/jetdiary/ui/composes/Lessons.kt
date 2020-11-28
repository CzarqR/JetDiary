package com.myniprojects.jetdiary.ui.composes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.myniprojects.jetdiary.db.lesson.Lesson
import com.myniprojects.jetdiary.ui.common.EditListState
import com.myniprojects.jetdiary.ui.common.EditableRow
import com.myniprojects.jetdiary.ui.common.ItemRowBase
import com.myniprojects.jetdiary.ui.theme.AppTheme
import com.myniprojects.jetdiary.ui.theme.mytypography
import timber.log.Timber


class LessonRow(override val editListState: EditListState<Lesson>) : EditableRow<Lesson>
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
        cancel: () -> Unit,
        delete: (Lesson) -> Unit,
        update: (Lesson) -> Unit
    )
    {
        EditLessonItem(
            lesson = item,
            onSave = save,
            onCancel = cancel,
            delete = delete,
            update = update
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
            style = mytypography.h4,
            textAlign = TextAlign.Start,
        )
    }
}


@Composable
fun EditLessonItem(
    lesson: Lesson,
    onSave: (Lesson) -> Unit,
    onCancel: () -> Unit,
    delete: (Lesson) -> Unit,
    update: (Lesson) -> Unit
)
{
    val (text, setText) = remember { mutableStateOf(lesson.name) }


    ItemRowBase {
        Row {
            TextField(
                modifier = Modifier
                    .weight(1f),
                value = text,
                onValueChange = {
                    setText(it)
                    update(lesson.copy(name = it))
                },
                maxLines = 1,
                backgroundColor = Color.Transparent,
                textStyle = mytypography.h5.copy(
                    color = AmbientContentColor.current
                )
            )

            IconButton(
                onClick = {
                    Timber.d("Save Button")
                    onSave(lesson)
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                icon = { Icon(asset = Icons.Outlined.Save) }
            )

            IconButton(
                onClick = {
                    onCancel()
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                icon = { Icon(asset = Icons.Outlined.Cancel) }
            )

            IconButton(
                onClick = {
                    Timber.d("Delete")
                    delete(lesson)
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                icon = { Icon(asset = Icons.Outlined.Delete) }
            )
        }
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
        EditLessonItem(Lesson("Mobile programing"), {}, {}, {}, {})
    }
}






