package ltd.nextalone.systemuitilehook

import ltd.nextalone.systemuitilehook.utils.*

object QuickTileNumHook {
    private var inited = false

    fun init() {
        if (inited) return
        inited = initOnce()
    }

    private fun initOnce() = tryOrFalse {
        logStart()
        "com.android.systemui.qs.QuickQSPanel".clazz?.method("setMaxTiles")?.hookBefore {
            it.args[0] = 8
        }
        "com.android.systemui.qs.TileLayout".clazz?.method("updateResources")?.hookAfter {
            it.thisObject.set("mColumns", 6)
            it.thisObject.set("mRows", 4)
        }
    }
}
