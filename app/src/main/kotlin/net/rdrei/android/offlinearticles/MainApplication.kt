package net.rdrei.android.offlinearticles

import android.app.Application
import com.parse.Parse
import com.parse.ParseCrashReporting

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        ParseCrashReporting.enable(this)
        Parse.enableLocalDatastore(this)
        Parse.initialize(
                this,
                BuildConfig.PARSE_APPLICATION_ID,
                BuildConfig.PARSE_MASTER_KEY)
    }
}
