package com.myniprojects.jetdiary.db.mark

import androidx.room.TypeConverter

class MarkConverter
{
    @TypeConverter
    fun fromMark(mark: Mark): String
    {
        return mark.name
    }

    @TypeConverter
    fun toMark(mark: String): Mark
    {
        return Mark.valueOf(mark)
    }
}