package ltd.nextalone.systemuitilehook.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout


const val MATCH_PARENT = -1
const val WRAP_CONTENT = -2


fun Context.getId(name: String): Int = resources.getIdentifier(name, "id", packageName)

fun Context.dip2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun Context.dip2sp(dpValue: Float): Int {
    val scale = resources.displayMetrics.density / resources.displayMetrics.scaledDensity
    return (dpValue * scale + 0.5f).toInt()
}

fun Context.px2sp(pxValue: Float): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}

internal val linearParams = LinearLayout.LayoutParams(0, 0)
internal val relativeParams = RelativeLayout.LayoutParams(0, 0)
internal val frameLayoutParams = FrameLayout.LayoutParams(0, 0)

internal fun View.hide() {
    this.visibility = View.GONE
    when (this.parent as ViewGroup) {
        is LinearLayout -> this.layoutParams = linearParams
        is RelativeLayout -> this.layoutParams = relativeParams
        is FrameLayout -> this.layoutParams = frameLayoutParams
    }
}


