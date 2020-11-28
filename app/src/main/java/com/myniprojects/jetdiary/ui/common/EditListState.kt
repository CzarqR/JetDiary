package com.myniprojects.jetdiary.ui.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class EditListState<T>(
    val flowList: Flow<List<T>>,
    private val onSave: (T) -> Unit,
    val onDelete: (T) -> Unit,
    val clickItem: (T) -> Unit
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
        currentEditItem = item
    }

    fun unselectItem()
    {
        currentEditItem = null
    }
}