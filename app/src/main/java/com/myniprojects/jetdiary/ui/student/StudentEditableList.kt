package com.myniprojects.jetdiary.ui.student

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.myniprojects.jetdiary.db.student.Student
import com.myniprojects.jetdiary.ui.common.EditListState
import com.myniprojects.jetdiary.ui.common.EditableRow
import com.myniprojects.jetdiary.ui.common.ItemRowBasee
import com.myniprojects.jetdiary.ui.theme.AppTheme
import com.myniprojects.jetdiary.ui.theme.AppTypographyy

class StudentRow(override val editListState: EditListState<Student>) : EditableRow<Student>
{

    @Composable
    override fun defaultItem(
        item: Student,
        onClick: (Student) -> Unit,
        onLongClick: (Student) -> Unit
    )
    {
        StudentItem(
            student = item,
            onCLick = onClick,
            onLongClick = onLongClick
        )
    }

    @Composable
    override fun editableItem(
        item: Student,
        save: (Student) -> Unit,
        cancel: () -> Unit,
        delete: (Student) -> Unit,
        update: (Student) -> Unit
    )
    {
        EditStudentItem(
            student = item,
            onSave = save,
            onCancel = cancel,
            delete = delete,
            update = update
        )
    }
}


@Composable
fun StudentItem(
    student: Student,
    onCLick: (Student) -> Unit,
    onLongClick: (Student) -> Unit
)
{
    ItemRowBasee {
        Row {
            Text(
                text = student.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = { onCLick(student) },
                        onLongClick = { onLongClick(student) }
                    )
                    .padding(8.dp),
                style = AppTypographyy.h4,
                textAlign = TextAlign.Start,
            )

//            Text(
//                text = student.surname,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clickable(
//                        onClick = { onCLick(student) },
//                        onLongClick = { onLongClick(student) }
//                    )
//                    .padding(8.dp),
//                style = AppTypographyy.h4,
//                textAlign = TextAlign.Start,
//            )
        }

    }
}


@Composable
fun EditStudentItem(
    student: Student,
    onSave: (Student) -> Unit,
    onCancel: () -> Unit,
    delete: (Student) -> Unit,
    update: (Student) -> Unit
)
{
    val (text, setText) = remember { mutableStateOf(student.name) }


    ItemRowBasee {
        Row {


        }
    }
}


@Preview(showBackground = true)
@Composable
fun LessonItemPreview()
{
    AppTheme {
        StudentItem(Student("Leo", "Messi"), {}, {})
    }
}


@Preview(showBackground = true)
@Composable
fun EditLessonItemPreview()
{
    AppTheme {
        EditStudentItem(Student("Leo", "Messi"), {}, {}, {}, {})
    }
}
