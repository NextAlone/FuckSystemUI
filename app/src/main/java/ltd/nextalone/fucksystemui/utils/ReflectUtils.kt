package ltd.nextalone.fucksystemui.utils

import de.robv.android.xposed.XposedHelpers
import ltd.nextalone.fucksystemui.HookEntry
import java.lang.reflect.Field

internal val String.clazz: Class<*>?
    get() = HookEntry.lpClassLoader?.loadClass(this)

internal fun Any.findField(name: String?, type: Class<*>?): Field? {
    if (name?.length!! > 0) {
        var clz: Class<*> = this.javaClass
        do {
            for (field in clz.declaredFields) {
                if ((type == null || field.type == type) && (field.name == name)
                ) {
                    field.isAccessible = true
                    return field
                }
            }
        } while (clz.superclass.also { clz = it } != null)
    }
    return null
}

internal fun Any.get(objName: String): Any? = this.get(objName, null)

internal fun <T> Any.get(name: String, type: Class<T>? = null): T? {
    try {
        val f: Field = this.javaClass.findField(name, type) as Field
        f.isAccessible = true
        return f[this] as T
    } catch (e: Exception) {
    }
    return null
}

internal fun Any.set(name: String, value: Any): Any = this.set(name, null, value)

internal fun Any.set(name: String, type: Class<*>?, value: Any) {
    try {
        val f: Field = this.javaClass.findField(name, type) as Field
        f.isAccessible = true
        f[this] = value
    } catch (e: java.lang.Exception) {
    }
}

internal fun Class<*>?.instance(vararg arg: Any?): Any = XposedHelpers.newInstance(this, *arg)

internal fun Class<*>?.instance(type: Array<Class<*>>, vararg arg: Any?): Any =
    XposedHelpers.newInstance(this, type, *arg)

internal fun Any?.invoke(name: String, vararg args: Any): Any? =
    XposedHelpers.findMethodBestMatch(this?.javaClass, name, * args).invoke(this, *args)

