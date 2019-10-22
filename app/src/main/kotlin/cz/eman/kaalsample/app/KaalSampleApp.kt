package cz.eman.kaalsample.app

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import cz.eman.kaalsample.BuildConfig
import cz.eman.kaalsample.app.di.appModule
import cz.eman.kaalsample.app.log.LogTree
import cz.eman.kaalsample.infrastructure.core.di.allApiModules
import cz.eman.kaalsample.presentation.feature.detail.di.detailModule
import cz.eman.kaalsample.presentation.feature.favorities.di.favoritesModule
import cz.eman.kaalsample.presentation.feature.login.di.loginModule
import cz.eman.kaalsample.presentation.feature.popularmovies.di.popularMoviesModule
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
            initFlipper()
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
            modules(appModule,
                    splashModule,
                    loginModule,
                    popularMoviesModule,
                    favoritesModule,
                    detailModule,
                    *allApiModules.toTypedArray()
            ) // Define others DI modules here
        }
    }

    private fun initFlipper() {
        SoLoader.init(this, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)

            // DB plugin
            client.addPlugin(DatabasesFlipperPlugin(applicationContext));

            // shared preferences plugin
            client.addPlugin(SharedPreferencesFlipperPlugin(applicationContext))

            // layout inspector plugin
            client.addPlugin(InspectorFlipperPlugin(applicationContext, DescriptorMapping.withDefaults()))

            client.start()
        }
    }
}
