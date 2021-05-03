package ltd.nextalone.fucksystemui.utils

import android.util.Log
import ltd.nextalone.fucksystemui.DEBUG
import ltd.nextalone.fucksystemui.TAG
import java.lang.reflect.Method


internal fun logDebug(msg: String) {
    if (DEBUG) Log.d(TAG, msg)
}

internal fun logError(msg: String) {
    Log.e(TAG, msg)
}

internal fun logThrowable(msg: String?, t: Throwable? = null) {
    Log.e(TAG, msg + t?.message, t)
}

internal fun <T : Any> T.logDetail(vararg msg: Any?) {
    logDebug("${this.javaClass.simpleName}: ${msg.joinToString(", ")}")
}

internal fun <T : Any> T.logStart() {
    logDebug("$this: Start")
}

internal fun <T : Any> T.logBefore(msg: String = "") {
    logDebug("$this: Before, $msg")
}

internal fun <T : Any> T.logAfter(msg: String = "") {
    logDebug("$this: After, $msg")
}

internal fun <T : Any> T.logMethod(method: Method?) {
    logDetail(
        "$this: Method", "name", method?.name, "return", method?.returnType, "param", *method?.parameterTypes
            ?: arrayOf("null")
    )
}
