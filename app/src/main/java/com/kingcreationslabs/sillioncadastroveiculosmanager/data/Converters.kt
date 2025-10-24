package com.kingcreationslabs.sillioncadastroveiculosmanager.data

import androidx.room.TypeConverter
import java.util.Date

class Converters {

    // Converte de Timestamp (Long) para Date
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    // Converte de Date para Timestamp (Long)
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}