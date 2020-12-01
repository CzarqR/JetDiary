package com.myniprojects.jetdiary.ui.mark

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.myniprojects.jetdiary.R
import com.myniprojects.jetdiary.db.mark.Mark
import com.myniprojects.jetdiary.db.mark.MarkAssigned
import com.myniprojects.jetdiary.ui.common.EditListState
import com.myniprojects.jetdiary.ui.common.EditableRow
import com.myniprojects.jetdiary.ui.common.ItemRowBase
import com.myniprojects.jetdiary.ui.theme.AppTheme
import com.myniprojects.jetdiary.ui.theme.AppTypography

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
}


@Composable
fun MarkItem(
    mark: MarkAssigned,
    onCLick: (MarkAssigned) -> Unit,
    onLongClick: (MarkAssigned) -> Unit
)
{
    ItemRowBase {
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
                style = AppTypography.h3.copy(fontWeight = FontWeight.Medium),
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


    ItemRowBase {
        Row {
            Text(text = "Todo")
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
