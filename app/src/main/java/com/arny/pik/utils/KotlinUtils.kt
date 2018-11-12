package com.arny.pik.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import io.reactivex.Observable
import kotlinx.coroutines.*
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.security.MessageDigest
import java.util.concurrent.TimeUnit
import kotlin.experimental.and
import kotlin.experimental.or

fun <T> find(list: List<T>, c: T, comp: Comparator<T>): T? {
    return list.firstOrNull { comp.compare(c, it) == 0 }
}

@JvmOverloads
fun transliterate(message: String, toUpper: Boolean = false): String {
    val abcCyr = charArrayOf(' ', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')
    val abcLat = arrayOf(" ", "a", "b", "v", "g", "d", "e", "e", "zh", "z", "i", "y", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "ts", "ch", "sh", "sch", "", "i", "", "e", "ju", "ja", "A", "B", "V", "G", "D", "E", "E", "Zh", "Z", "I", "Y", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "H", "Ts", "Ch", "Sh", "Sch", "", "I", "", "E", "Ju", "Ja", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
    val builder = StringBuilder()
    for (i in 0 until message.length) {
        for (x in abcCyr.indices) {
            if (message[i] == abcCyr[x]) {
                builder.append(abcLat[x])
            }
        }
    }
    var res = builder.toString()
    if (toUpper) {
        res = res.toUpperCase()
    }
    return res.trim()
}

/**
 * Универсальная функция окончаний
 * @param [count] число
 * @param [zero_other] слово с окончанием значения  [count] либо ноль,либо все остальные варианты включая от 11 до 19 (слов)
 * @param [one] слово с окончанием значения  [count]=1 (слово)
 * @param [two_four] слово с окончанием значения  [count]=2,3,4 (слова)
 */
fun getTermination(count: Int, zero_other: String, one: String, two_four: String): String {
    if (count % 100 in 11..19) {
        return count.toString() + " " + zero_other
    }
    return when (count % 10) {
        1 -> count.toString() + " " + one
        2, 3, 4 -> count.toString() + " " + two_four
        else -> count.toString() + " " + zero_other
    }
}

fun View?.setVisible(visible: Boolean) {
    this?.visibility = if (visible) View.VISIBLE else View.GONE
}

/**
 * safety String? to Double
 */
fun String?.setDouble(): Double {
    val source = this ?: ""
    if (source.isBlank() || source == ".") {
        return 0.0
    }
    return source.toDouble()
}

fun TextView?.setString(text: String?): TextView? {
    this?.clearFocus()
    this?.tag = ""
    this?.text = text
    this?.tag = null
    return this
}

fun animateVisible(v: View, visible: Boolean, duration: Int) {
    val alpha = if (visible) 1.0f else 0.0f
    v.clearAnimation()
    v.animate()
            .alpha(alpha)
            .setDuration(duration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    v.setVisible(visible)
                }
            })
}

fun hasIntentExtra(intent: Intent?, extraName: String): Boolean {
    val hasExtra = intent?.hasExtra(extraName)
    if (hasExtra != null) {
        return hasExtra
    }
    return false
}

fun <T> Intent?.getExtra(extraName: String): T? {
    return this?.extras?.get(extraName) as? T
}

fun <T> Bundle?.getExtra(extraName: String): T? {
    return this?.get(extraName) as? T
}

fun String?.isEmpty(): Boolean {
    return this.isNullOrBlank()
}

fun String?.ifEmpty(default: String): String {
    val blank = this?.isBlank() ?: true
    return if (blank) default else this!!
}

fun <T> HashMap<String, Any?>?.getOrNUll(key: String): T? {
    return this?.get(key) as? T
}

fun String?.parseLong(): Long? {
    return when {
        this == null -> null
        this.isBlank() -> null
        else -> {
            try {
                this.toLong()
            } catch (e: Exception) {
                null
            }
        }
    }
}

fun String?.parseDouble(): Double? {
    return when {
        this == null -> null
        this.isBlank() -> null
        else -> {
            try {
                this.toDouble()
            } catch (e: Exception) {
                null
            }
        }
    }
}

fun String?.parseInt(): Int? {
    return when {
        this == null -> null
        this.isBlank() -> null
        else -> {
            try {
                this.toInt()
            } catch (e: Exception) {
                null
            }
        }
    }
}

/**
 * Subscribe on function value
 * @param T function value(MUST NOT BE NULL)
 */
fun <T> subscribeOnFunctionValue(onLoadFunc: () -> T): Observable<T> {
    return Observable.create<T> { subscriber ->
        Observable.fromCallable { onLoadFunc() }
                .subscribe({ response ->
                    subscriber.onNext(response)
                }, { error ->
                    subscriber.onError(error)
                })
    }.retryWhen { errors ->
        errors.flatMap { throwable ->
            return@flatMap Observable.timer(1, TimeUnit.MILLISECONDS)
        }
    }
}

fun <T> launchAsync(block: suspend () -> T, onComplete: (T) -> Unit = {}, onError: (Throwable) -> Unit = {}, dispatcher: CoroutineDispatcher = Dispatchers.IO): Job {
    return CoroutineScope(Dispatchers.Main).launch {
        try {
            val result = CoroutineScope(dispatcher).async { block.invoke() }.await()
            onComplete.invoke(result)
        } catch (e: CancellationException) {
            Log.e("Execute Async", "canceled by user")
        } catch (e: Exception) {
            onError(e)
        }
    }
}

suspend fun <T> background(block: suspend () -> T): Deferred<T> {
    return GlobalScope.async(Dispatchers.IO) { block.invoke() }
}

fun getFileMD5(filename: String): String {
    val buffer = 8192
    val buf = ByteArray(buffer)
    var length: Int
    try {
        val fis = FileInputStream(filename)
        val bis = BufferedInputStream(fis)
        val md = MessageDigest.getInstance("MD5")
        while (true) {
            val read = bis.read(buf)
            if (read != -1) {
                length = read
                md.update(buf, 0, length)
            } else {
                break
            }
        }
        bis.close()
        val array = md.digest()
        val sb = StringBuilder()
        for (anArray in array) {
            sb.append(Integer.toHexString((anArray and 0xFF.toByte() or 0x100.toByte()).toInt()), 1, 3)
        }
        return sb.toString()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return "md5bad"
}



