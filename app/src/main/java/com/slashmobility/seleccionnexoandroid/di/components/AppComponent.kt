package com.slashmobility.seleccionnexoandroid.di.components


import android.app.Application
import com.slashmobility.seleccionnexoandroid.AppController
import com.slashmobility.seleccionnexoandroid.di.modules.*
import com.slashmobility.seleccionnexoandroid.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApiModule::class, DatabaseModule::class,
        ViewModelModule::class, AndroidSupportInjectionModule::class,
        ActivityModule::class, FragmentModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun apiModule(apiModule: ApiModule): Builder

        @BindsInstance
        fun dbModule(dbModule: DatabaseModule): Builder

        fun build(): AppComponent
    }

    fun inject(appController: AppController)
}
