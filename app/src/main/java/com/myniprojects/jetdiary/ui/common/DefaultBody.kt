package com.myniprojects.jetdiary.ui.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animate
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
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

@ExperimentalAnimationApi
@Composable
fun <T> DefaultEditableBody(
    editableRow: EditableRow<T>,
    state: LazyListState = rememberLazyListState(),
    showFab: Boolean = remember { true },
    setTitle: (String) -> Unit = {},
    title: String? = null
)
{
    val animatedElevation = animate(1.dp, TweenSpec(500))

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

            androidx.compose.animation.AnimatedVisibility(
                visible = true,
                initiallyVisible = false,
                enter = remember {
                    fadeIn(
                        animSpec = TweenSpec(
                            300,
                            easing = FastOutLinearInEasing
                        )
                    )
                },
                exit = remember {
                    fadeOut(
                        animSpec = TweenSpec(
                            100,
                            easing = FastOutSlowInEasing
                        )
                    )
                },
            )
            {
                Surface(
                    elevation = animatedElevation
                ) {

                    editableRow.addableItem(
                        item = addItem,
                        save = {
                            editableRow.editListState.insert(it)
                        },
                        update = {
                            setAddItem(it)
                        }
                    )
                }
            }


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
