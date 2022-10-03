package com.littlewind.android.base.functional

import java.lang.reflect.Type
import kotlin.reflect.KClass

interface Serializer {
    fun serialize(instance: Any?): String
    fun <T : Any> deserialize(serializedString: String, classOfT: KClass<T>): T
    fun <T : Any> deserialize(serializedString: String, typeOfT: Type): T
}