package co.lucaspinazzola.example.data.utils

import co.lucaspinazzola.example.domain.utils.Date
import com.soywiz.klock.DateFormat
import com.soywiz.klock.parse
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

@Serializer(forClass = Date::class)
object DateSerializer: KSerializer<Date> {
    val df = DateFormat("yyyy-MM-dd HH:mm:ss")

    override val descriptor: SerialDescriptor = PrimitiveDescriptor("WithCustomDefault", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, obj: Date) {
        //output.encode(df.format(obj))
    }

    override fun deserialize(decoder: Decoder): Date {
        return Date(df.parse(decoder.decodeString()).local.unixMillisLong)
    }
}
