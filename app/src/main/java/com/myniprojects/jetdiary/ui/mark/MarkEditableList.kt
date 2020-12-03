package com.myniprojects.jetdiary.ui.mark

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.myniprojects.jetdiary.R
import com.myniprojects.jetdiary.db.mark.Mark
import com.myniprojects.jetdiary.db.mark.MarkAssigned
import com.myniprojects.jetdiary.ui.common.EditListState
import com.myniprojects.jetdiary.ui.common.EditableRow
import com.myniprojects.jetdiary.ui.theme.AppTheme
import com.myniprojects.jetdiary.ui.theme.AppTypography
import timber.log.Timber
import java.util.*

class MarkRow(
    override val editListState: EditListState<MarkAssigned>
) : EditableRow<MarkAssigned>
{
    @Composable
    override fun defaultItem(
        item: MarkAssigned,
        onClick: (MarkAssigned) -> Unit,
        onLongClick: (MarkAssigned) -> Unit
    )
    {
        MarkItem(
            mark = item,
            onCLick = onClick,
            onLongClick = onLongClick
        )
    }

    @Composable
    override fun editableItem(
        item: MarkAssigned,
        save: (MarkAssigned) -> Unit,
        cancel: () -> Unit,
        delete: (MarkAssigned) -> Unit,
        update: (MarkAssigned) -> Unit
    )
    {
        MarkEditItem(
            mark = item,
            onSave = save,
            onCancel = cancel,
            delete = delete,
            update = update
        )
    }

    @Composable
    override fun addableItem(
        item: MarkAssigned,
        save: (MarkAssigned) -> Unit,
        update: (MarkAssigned) -> Unit
    )
    {
        AddableMarkItem(
            mark = item,
            onSave = save,
            update = update
        )
    }
}


@Composable
fun MarkItem(
    mark: MarkAssigned,
    onCLick: (MarkAssigned) -> Unit = {},
    onLongClick: (MarkAssigned) -> Unit = {}
)
{
    Row(
        modifier = Modifier
            .clickable(
                onClick = { onCLick(mark) },
                onLongClick = { onLongClick(mark) }
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = mark.mark.markValue.toString(),
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.default_padding_small),
                    end = dimensionResource(id = R.dimen.default_padding_small)
                ),
            style = AppTypography.h3,
            textAlign = TextAlign.Start,
        )

        Text(
            text = mark.note,
            modifier = Modifier
                .weight(1F)
                .align(Alignment.Top)
                .padding(start = dimensionResource(id = R.dimen.default_padding_small)),
            style = AppTypography.body1,
            textAlign = TextAlign.Start,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MarkItemPreview()
{
    AppTheme {
        MarkItem(MarkAssigned(
            1,
            1,
            Mark.THREE_HALF,
            "Some note about mark. It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. "
        ), {}, {})
    }
}

@Preview(showBackground = true)
@Composable
fun MarkItemPreviewLongText()
{
    AppTheme {
        MarkItem(MarkAssigned(
            1,
            1,
            Mark.FIVE,
            "Some note about mark. It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. Some note about mark. It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. "
        ), {}, {})
    }
}


@Preview(showBackground = true)
@Composable
fun MarkItemPreviewShortText()
{
    AppTheme {
        MarkItem(MarkAssigned(
            1,
            1,
            Mark.FIVE,
            "Short note"
        ), {}, {})
    }
}

@Composable
fun MarkEditItem(
    mark: MarkAssigned,
    onSave: (MarkAssigned) -> Unit,
    onCancel: () -> Unit,
    delete: (MarkAssigned) -> Unit,
    update: (MarkAssigned) -> Unit
)
{
    val (mar, setMar) = remember { mutableStateOf(mark) }

    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.default_padding_small))
            .fillMaxWidth()
    ) {


        MarkRow(
            mark = mar,
            setMark = setMar,
            update = update
        )


        Row {

            IconButton(
                onClick = {
                    delete(mark)
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                icon = { Icon(asset = Icons.Outlined.Delete) }
            )

            TextField(
                modifier = Modifier
                    .weight(1f),
                value = mar.note,
                onValueChange = {
                    val n = mar.copy(note = it)
                    setMar(n)
                    update(n)
                },
                onImeActionPerformed = { imeAction, _ ->
                    Timber.d("imeAction $imeAction")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                backgroundColor = Color.Transparent,
                textStyle = AppTypography.h5.copy(
                    color = AmbientContentColor.current
                ),
                label = {
                    Text(text = stringResource(id = R.string.note))
                },
                activeColor = MaterialTheme.colors.secondaryVariant
            )

            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.default_padding_small))
                    .align(Alignment.CenterVertically)
            ) {
                IconButton(
                    onClick = {
                        onSave(mar)
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
}


@Composable
fun MarkRow(
    mark: MarkAssigned,
    setMark: (MarkAssigned) -> Unit,
    update: (MarkAssigned) -> Unit
)
{

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Mark.values().forEach {

            Text(
                text = it.markText,
                modifier = Modifier
                    .weight(1F)
                    .clickable(
                        onClick =
                        {
                            val n = mark.copy(mark = it)
                            setMark(n)
                            update(n)
                        }),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                color =
                if (mark.mark == it)
                {
                    if (isSystemInDarkTheme())
                    {
                        MaterialTheme.colors.secondaryVariant
                    }
                    else
                    {
                        MaterialTheme.colors.primaryVariant
                    }
                }
                else
                {
                    AmbientContentColor.current
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MarkEditItem()
{
    AppTheme {
        MarkEditItem(MarkAssigned(
            1,
            1,
            Mark.THREE_HALF,
            "Some note about mark. It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. "
        ), {}, {}, {}, {})
    }
}


@Composable
fun AddableMarkItem(
    mark: MarkAssigned,
    onSave: (MarkAssigned) -> Unit,
    update: (MarkAssigned) -> Unit
)
{
    val (mar, setMar) = remember { mutableStateOf(mark) }

    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.default_padding_small))
            .fillMaxWidth()
    ) {


        MarkRow(
            mark = mar,
            setMark = setMar,
            update = update
        )

        Row {

            TextField(
                modifier = Modifier
                    .weight(1f),
                value = mar.note,
                onValueChange = {
                    val n = mar.copy(note = it)
                    setMar(n)
                    update(n)
                },
                onImeActionPerformed = { imeAction, _ ->
                    Timber.d("imeAction $imeAction")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                backgroundColor = Color.Transparent,
                textStyle = AppTypography.h5.copy(
                    color = AmbientContentColor.current
                ),
                label = {
                    Text(text = stringResource(id = R.string.note))
                },
                activeColor = MaterialTheme.colors.secondaryVariant
            )


            IconButton(
                onClick = {
                    onSave(mar.copy(date = Date(System.currentTimeMillis())))
                    val n = mar.copy(note = "")
                    setMar(n)
                    update(n)
                },
                modifier = Modifier.align(Alignment.CenterVertically),
                icon = { Icon(asset = Icons.Outlined.Save) }
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddableMarkItemPreview()
{
    AppTheme {
        AddableMarkItem(MarkAssigned(
            1,
            1,
            Mark.THREE_HALF,
            "Some note about mark. It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. "
        ), {}, {})
    }
}










