package com.myniprojects.jetdiary.ui.common


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.myniprojects.jetdiary.R


@Composable
fun ItemRowBase(
    bottomPadding: Dp? = null,
    body: @Composable () -> Unit,
)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.item_base_margin_h),
                end = dimensionResource(id = R.dimen.item_base_margin_h),
                top = dimensionResource(id = R.dimen.item_base_margin_v),
                bottom = bottomPadding ?: dimensionResource(id = R.dimen.item_base_margin_v)
            ),
        elevation = dimensionResource(id = R.dimen.card_elevation),
    ) {
        body()
    }

}


interface EditableRow<T>
{
    @Composable
    fun defaultItem(item: T, onClick: (T) -> Unit, onLongClick: (T) -> Unit)

    @Composable
    fun editableItem(
        item: T,
        save: (T) -> Unit,
        cancel: () -> Unit,
        delete: (T) -> Unit,
        update: (T) -> Unit
    )

    @Composable
    fun addableItem(
        item: T,
        save: (T) -> Unit,
        update: (T) -> Unit
    )

    val editListState: EditListState<T>
}

@Composable
fun <T> EditableList(
    editableRow: EditableRow<T>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState()
)
{
    val list: List<T> by editableRow.editListState.flowList.collectAsState(listOf())

    LazyColumnForIndexed(
        modifier = modifier
            .padding(
                top = dimensionResource(id = R.dimen.item_base_margin_v),
                bottom = dimensionResource(id = R.dimen.item_base_margin_v)
            ),
        items = list,
        state = state
    ) { i, item ->


        ItemRowBase(
            bottomPadding = if (i == list.size - 1) dimensionResource(id = R.dimen.last_card_padding) else null
        ) {
            if (item == editableRow.editListState.currentEditItem)
            {
                editableRow.editableItem(
                    item = item,
                    save = {
                        editableRow.editListState.updateDb()
                    },
                    cancel = {
                        editableRow.editListState.unselectItem()
                    },
                    delete = editableRow.editListState.onDelete,
                    update = {
                        editableRow.editListState.updateBuffer(it)
                    }
                )
            }
            else
            {
                editableRow.defaultItem(
                    item = item,
                    onClick = editableRow.editListState.clickItem,
                    onLongClick = {
                        editableRow.editListState.selectItem(it)
                    }
                )
            }
        }

    }
}
