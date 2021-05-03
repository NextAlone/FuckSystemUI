package ltd.nextalone.systemuitilehook

import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.IXposedHookZygoteInit.StartupParam
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam
import ltd.nextalone.systemuitilehook.utils.logDebug
import ltd.nextalone.systemuitilehook.utils.logDetail
import ltd.nextalone.systemuitilehook.utils.logError
import ltd.nextalone.systemuitilehook.utils.logThrowable
import java.io.File

class HookEntry : IXposedHookLoadPackage, IXposedHookZygoteInit {
    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        logDetail(lpparam.packageName)
        if (PACKAGE_NAME == lpparam.packageName) {
            if (!sInitialized) {
                sInitialized = true
                initializeHookInternal(lpparam)
            }
        }
    }

    override fun initZygote(startupParam: StartupParam) {
        modulePath = startupParam.modulePath
    }

    companion object {
        private var myClassLoader: ClassLoader? = null
        var lpClassLoader: ClassLoader? = null
        private var sInitialized = false
        private var modulePath: String? = null

    }

    private fun initializeHookInternal(lpparam: LoadPackageParam) {
        logDebug("Hooked")
        try {
            lpClassLoader = lpparam.classLoader
            logDebug("Hooked succeeded")
            QuickTileNumHook.init()
        } catch (e: Exception) {
            logThrowable("initializeHookInternal: ", e)
        }
    }

    fun injectModuleResources(res: Resources?) {
        if (res == null) {
            return
        }
        try {
            res.getString(R.string.res_inject_success)
            return
        } catch (ignored: NotFoundException) {
        }
        try {
            if (myClassLoader == null) {
                myClassLoader = HookEntry::class.java.classLoader
            }
            if (modulePath == null) {
                return
            }
            val assets = res.assets
            @SuppressLint("DiscouragedPrivateApi") val addAssetPath = AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
            addAssetPath.isAccessible = true
            val cookie = addAssetPath.invoke(assets, modulePath) as Int
            try {
                "injectModuleResources".logDetail(res.getString(R.string.res_inject_success))
            } catch (e: NotFoundException) {
                logError("Fatal: injectModuleResources: test injection failure!")
                logError("injectModuleResources: cookie=$cookie, path=$modulePath, loader=$myClassLoader")
                var length: Long = -1
                var read = false
                var exist = false
                var isDir = false
                try {
                    val f = File(modulePath!!)
                    exist = f.exists()
                    isDir = f.isDirectory
                    length = f.length()
                    read = f.canRead()
                } catch (e2: Throwable) {
                    logError(e2.toString())
                }
                logError("getModulePath: exists = $exist, isDirectory = $isDir, canRead = $read, fileLength = $length")
            }
        } catch (e: Exception) {
            logError(e.toString())
        }
    }


}
