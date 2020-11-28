package com.myniprojects.jetdiary.ui.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.Flow

class EditListState<T>(
    val flowList: Flow<List<T>>,
    private val onSave: (T) -> Unit,
    val onDelete: (T) -> Unit,
    val clickItem: (T) -> Unit,
    val generateNewItem: () -> T
)
{
    private var updateBuffer: T? = null

    fun save()
    {
        updateBuffer?.let {
            onSave(it)
        }
    }

    fun update(item: T)
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

    fun addAndEditNewItem()
    {
        val item = generateNewItem()

        onSave(item)
        selectItem(item)

    }
}