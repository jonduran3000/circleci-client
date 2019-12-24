package com.jonduran.circleci.cache

import androidx.room.TypeConverter

class Converters {
    @TypeConverter fun sourceControlToInt(value: SourceControl): Int = value.ordinal
    @TypeConverter fun intToSourceControl(value: Int): SourceControl = SourceControl.values()[value]
}