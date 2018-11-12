package com.arny.pik.utils

import com.google.gson.Gson
import com.google.gson.JsonArray
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.util.*
import kotlin.collections.HashMap

fun <T> fromJson(response: Any?, cls: Class<*>): T? {
    if (response != null) {
        return fromJson(response, cls, Gson())
    }
    return null
}

fun <T> fromJson(response: Any, cls: Class<*>, gson: Gson): T {
    val name = response.javaClass.simpleName
    val t = gson.fromJson(response.toString(), cls) as T
    return t
}

@JvmOverloads
fun toJson(obj: Any, gson: Gson? = Gson()): String {
    return gson!!.toJson(obj)
}

fun mapToJson(map: HashMap<String, Any>): JSONObject {
    val jsonObject = JSONObject()
    for (mutableEntry in map) {
        val key = mutableEntry.key
        val value = mutableEntry.value
        jsonObject.put(key, value)
    }
    return jsonObject
}

fun jsonObjectFillHashMap(vararg params: JSONObject?, map: HashMap<String, Any>): HashMap<String, Any> {
    for (param in params) {
        if (param != null) {
            map.putAll(getJsonObjectToHashMap(param))
        }
    }
    return map
}

fun <T> convertArray(jArr: JsonArray, clazz: Class<Any>): ArrayList<T> {
    val list = ArrayList<T>()
    try {
        var i = 0
        val l = jArr.size()
        while (i < l) {
            list.add(Gson().fromJson<Any>(jArr.get(i), clazz) as T)
            i++
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return list
}

fun getAristosRequestUrl(host: String): String {
    val aristosUrl = "aristos.pw"
    var start = host.indexOf(aristosUrl)
    var length = aristosUrl.length
    if (start == -1) {
        length = 0
        start = 0
    }
    return host.substring(start + length, host.length)
}

fun getRequestBody(params: HashMap<String, Any>): RequestBody {
    return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONObject(params).toString())
}

fun getJsonObjectToHashMap(params: JSONObject): HashMap<String, Any> {
    val mapParams = HashMap<String, Any>()
    try {
        for ((key, value) in jsonToMap(params)) {
            mapParams[key] = value
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return mapParams
}

@Throws(JSONException::class)
fun jsonToMap(json: JSONObject): Map<String, Any> {
    var retMap: Map<String, Any> = HashMap()
    if (json !== JSONObject.NULL) {
        retMap = toMap(json)
    }
    return retMap
}

@Throws(JSONException::class)
private fun toMap(obj: JSONObject): Map<String, Any> {
    val map = HashMap<String, Any>()
    val keysItr = obj.keys()
    while (keysItr.hasNext()) {
        val key = keysItr.next()
        var value = obj.get(key)
        if (value is JSONArray) {
            value = toList(value)
        } else if (value is JSONObject) {
            value = toMap(value)
        }
        map[key] = value
    }
    return map
}

@Throws(JSONException::class)
private fun toList(array: JSONArray): List<Any> {
    val list = ArrayList<Any>()
    for (i in 0 until array.length()) {
        var value = array.get(i)
        if (value is JSONArray) {
            value = toList(value)
        } else if (value is JSONObject) {
            value = toMap(value)
        }
        list.add(value)
    }
    return list
}

fun getResponseError(throwable: Throwable): String {
    var error = ""
    var code: Int
    try {
        if (throwable is HttpException) {
            code = throwable.code()
            val result = throwable.response()?.errorBody()?.string()
            try {
                val responseError = JSONObject(result)
                code = responseError.getInt("code")
                val message = StringBuilder()
                message.append("$code;")
                if (responseError.has("error")) {
                    message.append(responseError.getString("error")).append(";")
                }
                if (responseError.has("message")) {
                    message.append("Message:${responseError.getString("message").trim()}").append(";")
                }
                if (throwable.message != null) {
                    message.append("Ошибка сервера:${throwable.message}").append(";")
                }
                error = message.toString()
            } catch (e: JSONException) {
                e.printStackTrace()
                error = result.toString()
            }

            when (code) {
                504 -> error = "Время ожидания истекло,повторите запрос позже"
                503 -> error = "Сервис временно недоступен,повторите запрос позже"
            }
        } else {
            val message = throwable.message ?: "Ошибка запроса"
            val contains = when {
                message.contains("Unable to resolve host") -> true
                message.contains("Failed to connect") -> true
                message.contains("software caused connection abort", true) -> true
                else -> false
            }
            error = if (contains) {
                "Ошибка соединения, адрес недоступен"
            } else {
                message
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return error
}