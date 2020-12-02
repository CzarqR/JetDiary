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
    val clickItem: (T) -> Unit,
    val generateNewItem: () -> T,
    val formatItem: (T) -> T
)
{
    private var updateBuffer: T? = null

    fun save()
    {
        Timber.d("Save")
        updateBuffer?.let {
            Timber.d("Save")
            onSave(formatItem(it))
        }
    }

    fun update(item: T)
    {
        Timber.d(item.toString())
        Timber.d("Update")
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