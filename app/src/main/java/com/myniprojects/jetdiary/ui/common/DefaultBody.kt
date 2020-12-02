package com.myniprojects.jetdiary.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.myniprojects.jetdiary.R

@Composable
fun <T> DefaultEditableBody(
    editableRow: EditableRow<T>,
    state: LazyListState = rememberLazyListState(),
    showFab: Boolean = remember { true },
    setTitle: (String) -> Unit = {},
    title: String? = null
)
{
    val (addState, setAddState) = remember { mutableStateOf(false) }
    val (addItem, setAddItem) = remember { mutableStateOf(editableRow.editListState.generateNewItem()) }

    title?.let {
        setTitle(it)
    }

    Column(
        modifier = Modifier
    ) {

        if (addState)
        {

            editableRow.addableItem(
                item = addItem,
                save = {
                    editableRow.editListState.insert(it)
                },
                update = {
                    setAddItem(it)
                }
            )

            Divider(modifier = Modifier.padding(4.dp))

        }

        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            EditableList(
                editableRow = editableRow,
                modifier = Modifier
                    .fillMaxSize(),
                state = state
            )

            if (showFab)
            {
                FloatingActionButton(
                    onClick = {
                        setAddState(!addState)
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(dimensionResource(id = R.dimen.fab_padding))

                ) {
                    if (addState)
                    {
                        Icon(asset = Icons.Outlined.ArrowUpward)
                    }
                    else
                    {
                        Icon(asset = Icons.Outlined.Add)
                    }
                }
            }
        }
    }
}
