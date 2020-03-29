package com.jonduran.circleci.remote.serializers

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import java.time.Instant
import java.time.format.DateTimeFormatter

@Serializer(forClass = Instant::class)
object InstantSerializer : KSerializer<Instant> {
    override fun serialize(encoder: Encoder, obj: Instant) {
        encoder.encodeString(DateTimeFormatter.ISO_INSTANT.format(obj))
    }

    override fun deserialize(decoder: Decoder): Instant {
        return Instant.parse(decoder.decodeString())
    }
}