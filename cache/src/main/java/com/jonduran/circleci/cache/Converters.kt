package com.jonduran.circleci.cache

import androidx.room.TypeConverter
import com.jonduran.circleci.cache.entity.SourceControl
import com.jonduran.circleci.cache.entity.Status
import org.threeten.bp.Instant
import org.threeten.bp.format.DateTimeFormatter.ISO_INSTANT
import java.util.UUID

class Converters {
    @TypeConverter fun sourceControlToInt(value: SourceControl): Int = value.ordinal
    @TypeConverter fun intToSourceControl(value: Int): SourceControl = SourceControl.values()[value]
    @TypeConverter fun instantToString(value: Instant?): String? {
        return if (value == null) null else ISO_INSTANT.format(value)
    }
    @TypeConverter fun stringToInstant(value: String?): Instant? {
        return if (value == null) null else Instant.parse(value)
    }
    @TypeConverter fun statusToInt(value: Status): Int = value.ordinal
    @TypeConverter fun intToStatus(value: Int): Status = Status.values()[value]
    @TypeConverter fun uuidToString(value: UUID): String = value.toString()
    @TypeConverter fun stringToUuid(value: String): UUID = UUID.fromString(value)
}