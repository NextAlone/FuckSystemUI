package ltd.nextalone.systemuitilehook.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import ltd.nextalone.systemuitilehook.PACKAGE_NAME

internal fun Context.hostId(name: String): Int = this.resources.getIdentifier(name, "id", "com.android.packageinstaller")

internal fun Context.hostString(name: String): String = this.getString(this.resources.getIdentifier(name, "String", "com.android.packageinstaller"))

internal fun Any.hostId(name: String): Int? {
    return this.getIdentifier("id", name)
}

internal fun Any.hostLayout(name: String): Int? {
    return this.getIdentifier("layout", name)
}

internal fun Any.hostDrawable(name: String): Int? {
    return this.getIdentifier("drawable", name)
}

internal fun Any.getIdentifier(defType: String, name: String): Int? {
    return when (this) {
        is View -> this.resources.getIdentifier(name, defType, PACKAGE_NAME)
        is Context -> this.resources.getIdentifier(name, defType, PACKAGE_NAME)
        else -> null
    }
}

internal fun <T : View?> Any.findHostView(name: String): T? {
    return when (this) {
        is View -> this.hostId(name)?.let { this.findViewById<T>(it) }
        is Activity -> this.hostId(name).let { this.findViewById<T>(it) }
        is Dialog -> this.hostId(name)?.let { this.findViewById<T>(it) }
        else -> null
    }
}
