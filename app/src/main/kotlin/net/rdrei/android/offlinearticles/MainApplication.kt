package net.rdrei.android.offlinearticles

import android.app.Application
import android.os.Build
import com.parse.Parse
import com.parse.ParseCrashReporting
import com.parse.ParseObject
import net.rdrei.android.offlinearticles.model.Bookmark
import rx.plugins.DebugHook
import rx.plugins.RxJavaPlugins
import rx.plugins.SimpleDebugNotificationListener

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (!BuildConfig.DEBUG) {
            ParseCrashReporting.enable(this)
        }

        Parse.enableLocalDatastore(this)
        ParseObject.registerSubclass(Bookmark::class.java)

        Parse.initialize(
                this,
                BuildConfig.PARSE_APPLICATION_ID,
                BuildConfig.PARSE_CLIENT_KEY)
    }
}
