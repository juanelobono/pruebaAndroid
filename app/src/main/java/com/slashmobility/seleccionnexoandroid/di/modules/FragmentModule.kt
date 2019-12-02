package com.slashmobility.seleccionnexoandroid.di.modules

import com.slashmobility.seleccionnexoandroid.ui.detail.GroupDetailFragment
import com.slashmobility.seleccionnexoandroid.ui.main.GroupFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeGroupFragment(): GroupFragment

    @ContributesAndroidInjector
    abstract fun contributeGroupDetailFragment(): GroupDetailFragment
}