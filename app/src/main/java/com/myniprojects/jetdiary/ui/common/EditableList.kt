package com.myniprojects.jetdiary.ui.common


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.myniprojects.jetdiary.R

@ExperimentalAnimationApi
@Composable
fun ItemRowBase(
    bottomPadding: Dp? = null,
    visible: Boolean = true,
    initialVisibility: Boolean = false,
    modifier: Modifier = Modifier,
    body: @Composable () -> Unit,
)
{
    AnimatedVisibility(
        visible = visible,
        initiallyVisible = initialVisibility,
        enter = remember { fadeIn(animSpec = TweenSpec(300, easing = FastOutLinearInEasing)) },
        exit = remember { fadeOut(animSpec = TweenSpec(100, easing = FastOutSlowInEasing)) },
    ) {
        Card(
            modifier = modifier
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

@ExperimentalAnimationApi
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