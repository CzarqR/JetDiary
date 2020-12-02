package com.myniprojects.jetdiary.ui.studenteditor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import com.myniprojects.jetdiary.R
import com.myniprojects.jetdiary.db.student.Student
import com.myniprojects.jetdiary.ui.common.EditListState
import com.myniprojects.jetdiary.ui.common.EditableRow
import com.myniprojects.jetdiary.ui.theme.AppTheme
import com.myniprojects.jetdiary.ui.theme.AppTypography
import timber.log.Timber

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

    @Composable
    override fun addableItem(item: Student, save: (Student) -> Unit, update: (Student) -> Unit)
    {
        AddableStudentItem(
            student = item,
            onSave = save,
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

    Row(
        modifier = Modifier
            .clickable(
                onClick = { onCLick(student) },
                onLongClick = { onLongClick(student) }
            )
            .padding(dimensionResource(id = R.dimen.default_padding_small))
    ) {

        Text(
            text = student.surname,
            modifier = Modifier.alignByBaseline(),
            style = AppTypography.h4,
            textAlign = TextAlign.Start,
        )

        Text(
            text = student.name,
            modifier = Modifier
                .padding(start = dimensionResource(id = R.dimen.default_padding_small))
                .alignByBaseline()
                .drawOpacity(0.8f),
            style = AppTypography.h5,
            textAlign = TextAlign.Start,
        )
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
    val (stud, setStudent) = remember { mutableStateOf(student) }


    Row(
        modifier = Modifier.wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically

    ) {

        // delete column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    Timber.d("Delete")
                    delete(student)
                },
                modifier = Modifier,
                icon = { Icon(asset = Icons.Outlined.Delete) }
            )
        }

        // TextField column
        Column(
            modifier = Modifier.weight(1f)
        ) {


            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = stud.surname,
                onValueChange = {
                    val n = stud.copy(surname = it)
                    setStudent(n)
                    update(n)
                },
                onImeActionPerformed = { imeAction, _ ->
                    Timber.d("imeAction $imeAction")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                isErrorValue = stud.surname.isBlank(),
                maxLines = 1,
                backgroundColor = Color.Transparent,
                textStyle = AppTypography.h5.copy(
                    color = AmbientContentColor.current
                ),
                label = {
                    Text(text = stringResource(id = R.string.name))
                },
                activeColor = MaterialTheme.colors.secondaryVariant
            )



            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = stud.name,
                onValueChange = {
                    setStudent(stud.copy(name = it))
                    update(stud)
                },
                onImeActionPerformed = { imeAction, _ ->
                    Timber.d("imeAction $imeAction")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                isErrorValue = stud.name.isBlank(),
                maxLines = 1,
                backgroundColor = Color.Transparent,
                textStyle = AppTypography.h5.copy(
                    color = AmbientContentColor.current
                ),
                label = {
                    Text(text = stringResource(id = R.string.surname))
                },
                activeColor = MaterialTheme.colors.secondaryVariant
            )
        }

        // save cancel column
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding_small))
        ) {
            IconButton(
                onClick = {
                    Timber.d("Save Button")
                    if (stud.surname.isNotBlank() && stud.name.isNotBlank())
                    {
                        onSave(stud)
                    }
                },
                modifier = Modifier,
                icon = { Icon(asset = Icons.Outlined.Save) }
            )

            IconButton(
                onClick = {
                    onCancel()
                },
                modifier = Modifier,
                icon = { Icon(asset = Icons.Outlined.Close) }
            )
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


@Composable
fun AddableStudentItem(
    student: Student,
    onSave: (Student) -> Unit,
    update: (Student) -> Unit
)
{
    val (stud, setStudent) = remember { mutableStateOf(student) }

    Row(
        modifier = Modifier
            .padding(
                start = dimensionResource(id = R.dimen.item_base_margin_h),
                end = dimensionResource(id = R.dimen.item_base_margin_h),
                top = dimensionResource(id = R.dimen.item_base_margin_v),
            ),
        verticalAlignment = Alignment.CenterVertically

    ) {

        // TextField column
        Column(
            modifier = Modifier.weight(1f)
        ) {


            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = stud.surname,
                onValueChange = {
                    val n = stud.copy(surname = it)
                    setStudent(n)
                    update(n)
                },
                onImeActionPerformed = { imeAction, _ ->
                    Timber.d("imeAction $imeAction")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                isErrorValue = stud.surname.isBlank(),
                maxLines = 1,
                backgroundColor = Color.Transparent,
                textStyle = AppTypography.h5.copy(
                    color = AmbientContentColor.current
                ),
                label = {
                    Text(text = stringResource(id = R.string.name))
                },
                activeColor = MaterialTheme.colors.secondaryVariant
            )



            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = stud.name,
                onValueChange = {
                    setStudent(stud.copy(name = it))
                    update(stud)
                },
                onImeActionPerformed = { imeAction, _ ->
                    Timber.d("imeAction $imeAction")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                isErrorValue = stud.name.isBlank(),
                maxLines = 1,
                backgroundColor = Color.Transparent,
                textStyle = AppTypography.h5.copy(
                    color = AmbientContentColor.current
                ),
                label = {
                    Text(text = stringResource(id = R.string.surname))
                },
                activeColor = MaterialTheme.colors.secondaryVariant
            )
        }

        IconButton(
            onClick = {
                Timber.d("Save Button")
                if (stud.surname.isNotBlank() && stud.name.isNotBlank())
                {
                    onSave(stud)
                    setStudent(Student())
                }
            },
            icon = { Icon(asset = Icons.Outlined.Save) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddableLessonItemPreview()
{
    AppTheme {
        AddableStudentItem(Student("Leo", "Messi"), {}, {})
    }
}