package ru.turizmryazan

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.facebook.stetho.Stetho
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import ru.turizmryazan.di.component.DaggerComponent
import ru.turizmryazan.di.component.DaggerDaggerComponent
import ru.turizmryazan.di.module.AppModule
import ru.turizmryazan.utils.Constants

class TurizmApp: Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        Stetho.initializeWithDefaults(this)
        daggerComponent = DaggerDaggerComponent.builder()
                .appModule(AppModule(this))
                .build()

        RxJavaPlugins.setErrorHandler { throwable: Throwable? ->
            if (throwable is UndeliverableException) {
                Log.d(Constants.LOGS_TAG, "Ошибка RxJava не доставлен результат ${throwable.message}")
            }
        }
    }

    companion object {
        var daggerComponent: DaggerComponent? = null
            private set
    }
}