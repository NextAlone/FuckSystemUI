package ltd.nextalone.systemuitilehook.utils

internal inline fun <reified T : Any> T.tryOrFalse(crossinline function: () -> Unit): Boolean {
    return try {
        function()
        true
    } catch (t: Throwable) {
        logThrowable(T::class.simpleName, t)
        false
    }
}

internal inline fun <reified T : Any> T.tryOrNull(crossinline function: () -> Unit): Any? {
    return try {
        function()
    } catch (t: Throwable) {
        logThrowable(T::class.simpleName, t)
        null
    }
}
