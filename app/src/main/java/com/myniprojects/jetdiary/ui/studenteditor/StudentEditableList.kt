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

class StudentRow(override val editListState: EditListState<Pair<Student, Boolean>>) : EditableRow<Pair<Student, Boolean>>
{
    @Composable
    override fun defaultItem(
        item: Pair<Student, Boolean>,
        onClick: (Pair<Student, Boolean>) -> Unit,
        onLongClick: (Pair<Student, Boolean>) -> Unit
    )
    {
        StudentCheckItem(
            student = item,
            onCLick = onClick,
            onLongClick = onLongClick
        )
    }

    @Composable
    override fun editableItem(
        item: Pair<Student, Boolean>,
        save: (Pair<Student, Boolean>) -> Unit,
        cancel: () -> Unit,
        delete: (Pair<Student, Boolean>) -> Unit,
        update: (Pair<Student, Boolean>) -> Unit
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
    override fun addableItem(
        item: Pair<Student, Boolean>,
        save: (Pair<Student, Boolean>) -> Unit,
        update: (Pair<Student, Boolean>) -> Unit
    )
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
fun StudentCheckItem(
    student: Pair<Student, Boolean>,
    onCLick: (Pair<Student, Boolean>) -> Unit,
    onLongClick: (Pair<Student, Boolean>) -> Unit
)
{

    val (isChecked, setIsChecked) = remember { mutableStateOf(student.second) }

    Row(
        modifier = Modifier
            .clickable(
                onClick = { },
                onLongClick = { onLongClick(student) }
            )
            .padding(dimensionResource(id = R.dimen.default_padding_small))
    ) {

        Checkbox(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(dimensionResource(id = R.dimen.default_padding_small)),
            checked = isChecked,
            onCheckedChange = {
                Timber.d("Click")
                onCLick(student.first to it)
                setIsChecked(it)
            })

        Text(
            text = student.first.surname,
            modifier = Modifier.alignByBaseline(),
            style = AppTypography.h4,
            textAlign = TextAlign.Start,
        )

        Text(
            text = student.first.name,
            modifier = Modifier
                .padding(start = dimensionResource(id = R.dimen.default_padding_small))
                .alignByBaseline()
                .drawOpacity(0.8f),
            style = AppTypography.h5,
            textAlign = TextAlign.Start,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LessonItemPreview()
{
    AppTheme {
        StudentCheckItem(Student("Leo", "Messi") to false, {}, {})
    }
}

@Composable
fun EditStudentItem(
    student: Pair<Student, Boolean>,
    onSave: (Pair<Student, Boolean>) -> Unit,
    onCancel: () -> Unit,
    delete: (Pair<Student, Boolean>) -> Unit,
    update: (Pair<Student, Boolean>) -> Unit
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
                value = stud.first.surname,
                onValueChange = {
                    val n = stud.copy(first = stud.first.copy(surname = it))
                    setStudent(n)
                    update(n)
                },
                onImeActionPerformed = { imeAction, _ ->
                    Timber.d("imeAction $imeAction")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                isErrorValue = stud.first.surname.isBlank(),
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
                value = stud.first.name,
                onValueChange = {
                    val n = stud.copy(first = stud.first.copy(name = it))
                    setStudent(n)
                    update(n)
                },
                onImeActionPerformed = { imeAction, _ ->
                    Timber.d("imeAction $imeAction")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                isErrorValue = stud.first.name.isBlank(),
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
                    if (stud.first.surname.isNotBlank() && stud.first.name.isNotBlank())
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
fun StudentItemPreview()
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
        EditStudentItem(Student("Leo", "Messi") to true, {}, {}, {}, {})
    }
}


@Composable
fun AddableStudentItem(
    student: Pair<Student, Boolean>,
    onSave: (Pair<Student, Boolean>) -> Unit,
    update: (Pair<Student, Boolean>) -> Unit
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
                value = stud.first.surname,
                onValueChange = {
                    val n = stud.copy(first = stud.first.copy(surname = it))
                    setStudent(n)
                    update(n)
                },
                onImeActionPerformed = { imeAction, _ ->
                    Timber.d("imeAction $imeAction")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                isErrorValue = stud.first.surname.isBlank(),
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
                value = stud.first.name,
                onValueChange = {
                    setStudent(stud.copy(first = stud.first.copy(name = it)))
                    update(stud)
                },
                onImeActionPerformed = { imeAction, _ ->
                    Timber.d("imeAction $imeAction")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                isErrorValue = stud.first.name.isBlank(),
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
                if (stud.first.surname.isNotBlank() && stud.first.name.isNotBlank())
                {
                    onSave(stud.copy(second = false))
                    setStudent(Student() to false)
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
        AddableStudentItem(Student("Leo", "Messi") to true, {}, {})
    }
}