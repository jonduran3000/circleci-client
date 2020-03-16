package com.jonduran.circleci.remote.serializers

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import java.util.UUID

@Serializer(forClass = UUID::class)
object UuidSerializer : KSerializer<UUID> {
    override fun serialize(encoder: Encoder, obj: UUID) {
        encoder.encodeString(obj.toString())
    }

    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }
}