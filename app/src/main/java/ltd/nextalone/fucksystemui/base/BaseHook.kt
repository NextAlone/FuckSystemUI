package ltd.nextalone.fucksystemui.base

open class BaseHook {
    private var inited = false

    fun init() {
        if (inited) return
        inited = initOnce()
    }

    open fun initOnce(): Boolean = true
}
