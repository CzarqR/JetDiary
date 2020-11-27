package com.myniprojects.jetdiary.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.Flow

class EditListState<T>(
    val flowList: Flow<List<T>>,
    val onSave: (T) -> Unit,
    val onDelete: (T) -> Unit,
    val clickItem: (T) -> Unit
)
{
    var currentEditItem: T? by mutableStateOf(null)


    fun selectItem(item: T)
    {
        currentEditItem = item
    }

    fun unselectItem()
    {
        currentEditItem = null
    }
}