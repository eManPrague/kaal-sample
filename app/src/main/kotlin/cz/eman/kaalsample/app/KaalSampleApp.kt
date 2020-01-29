package cz.eman.kaalsample.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import cz.eman.kaalsample.BuildConfig
import cz.eman.kaalsample.app.di.appModule
import cz.eman.kaalsample.app.log.LogTree
import cz.eman.kaalsample.infrastructure.core.di.allApiModules
import cz.eman.kaalsample.presentation.feature.detail.di.detailModule
import cz.eman.kaalsample.presentation.feature.favorities.di.favoritesModule
import cz.eman.kaalsample.presentation.feature.login.di.loginModule
import cz.eman.kaalsample.presentation.feature.splash.di.splashModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * @author eMan s.r.o. (info@eman.cz)
 * @see[Application]
 */
class KaalSampleApp : Application() {

    companion object {
        lateinit var instance: KaalSampleApp
    }

    override fun onCreate() {
        instance = this
        super.onCreate()

        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this)

            initKoin()
            initStetho()
            initMisc()
        }
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            // Create an InitializerBuilder
            val initializerBuilder = Stetho.newInitializerBuilder(this)
            with(initializerBuilder) {
                // Enable Chrome DevTools
                enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this@KaalSampleApp))
                // Enable command line interface
                enableDumpapp(Stetho.defaultDumperPluginsProvider(this@KaalSampleApp))
            }.build().apply {
                // Use the InitializerBuilder to generate an Initializer
                // Initialize Stetho with the Initializer
                Stetho.initialize(this)
            }
        }
    }

    private fun initMisc() {
        Timber.plant(LogTree())
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@KaalSampleApp)
            modules(
                appModule +
                        splashModule +
                        loginModule +
                        favoritesModule +
                        detailModule +
                        allApiModules
            ) // Define others DI modules here
        }
    }
}
