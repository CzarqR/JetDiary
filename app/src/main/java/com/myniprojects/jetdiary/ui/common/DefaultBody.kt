package com.myniprojects.jetdiary.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.myniprojects.jetdiary.R

@Composable
fun <T> DefaultBody(
    editableRow: EditableRow<T>,
    state: LazyListState = rememberLazyListState(),
    showFab: Boolean = remember { true },
    fabClick: () -> Unit = { },
)
{
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                onClick = fabClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(dimensionResource(id = R.dimen.fab_padding))

            ) {
                Icon(asset = Icons.Outlined.Add)
            }
        }
    }
}
