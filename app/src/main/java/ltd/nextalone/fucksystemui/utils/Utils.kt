package ltd.nextalone.fucksystemui.utils

internal inline fun tryOrFalse(crossinline function: () -> Unit): Boolean {
    return try {
        function()
        true
    } catch (t: Throwable) {
        logThrowable("Throwable", t)
        false
    }
}

internal inline fun tryOrNull(crossinline function: () -> Unit): Any? {
    return try {
        function()
    } catch (t: Throwable) {
        logThrowable("Throwable", t)
        null
    }
}
