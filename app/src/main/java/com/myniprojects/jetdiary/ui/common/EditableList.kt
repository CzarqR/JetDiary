package com.myniprojects.jetdiary.ui.common


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.myniprojects.jetdiary.R
import com.myniprojects.jetdiary.db.lesson.Lesson
import com.myniprojects.jetdiary.ui.composes.EditLessonItem
import com.myniprojects.jetdiary.ui.composes.LessonItem
import com.myniprojects.jetdiary.utils.EditListState


@Composable
fun ItemRowBase(
    body: @Composable () -> Unit
)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.item_base_margin_h),
                end = dimensionResource(id = R.dimen.item_base_margin_h),
                top = dimensionResource(id = R.dimen.item_base_margin_v),
                bottom = dimensionResource(id = R.dimen.item_base_margin_v)
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
    fun editableItem(item: T, save: (T) -> Unit, cancel: () -> Unit)
}

@Composable
fun <T> EditableList(
    editListState: EditListState<T>,
    editableRow: EditableRow<T>
)
{
    val list: List<T> by editListState.flowList.collectAsState(listOf())

    LazyColumnFor(
        modifier = Modifier
            .padding(
                top = dimensionResource(id = R.dimen.item_base_margin_v),
                bottom = dimensionResource(id = R.dimen.item_base_margin_v)
            ),
        items = list
    ) { item ->
        if (item == editListState.currentEditItem)
        {
            editableRow.editableItem(
                item = item,
                save = editListState.onSave,
                cancel = {
                    editListState.unselectItem()
                }
            )
        }
        else
        {
            editableRow.defaultItem(
                item = item,
                onClick = editListState.clickItem,
                onLongClick = {
                    editListState.selectItem(it)
                }
            )
        }


    }
}
