package com.myniprojects.jetdiary.ui.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.Flow

class EditListState<T>(
    val flowList: Flow<List<T>>,
    private val update: (T) -> Unit,
    val insert: (T) -> Unit,
    val onDelete: (T) -> Unit,
    val clickItem: (T) -> Unit,
    val generateNewItem: () -> T,
    val formatItem: (T) -> T
)
{
    private var updateBuffer: T? = null

    fun updateDb()
    {
        updateBuffer?.let {
            update(formatItem(it))
        }
    }

    fun updateBuffer(item: T)
    {
        updateBuffer = item
    }

    var currentEditItem: T? by mutableStateOf(null)


    fun selectItem(item: T)
    {
        updateBuffer = null
        currentEditItem = item
    }

    fun unselectItem()
    {
        currentEditItem = null
    }
}