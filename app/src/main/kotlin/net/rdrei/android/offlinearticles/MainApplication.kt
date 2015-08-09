package net.rdrei.android.offlinearticles

import android.app.Application
import com.parse.Parse
import com.parse.ParseCrashReporting
import com.parse.ParseObject
import net.rdrei.android.offlinearticles.model.Bookmark

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        ParseCrashReporting.enable(this)
        Parse.enableLocalDatastore(this)

        ParseObject.registerSubclass(javaClass<Bookmark>())

        Parse.initialize(
                this,
                BuildConfig.PARSE_APPLICATION_ID,
                BuildConfig.PARSE_MASTER_KEY)
    }
}
