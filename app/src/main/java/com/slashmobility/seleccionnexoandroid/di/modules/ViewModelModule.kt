package com.slashmobility.seleccionnexoandroid.di.modules


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.slashmobility.seleccionnexoandroid.factory.ViewModelFactory
import com.slashmobility.seleccionnexoandroid.ui.main.GroupViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GroupViewModel::class)
    protected abstract fun groupViewModel(groupViewModel: GroupViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(GroupDetailViewModel::class)
//    protected abstract fun groupDetailViewModel(groupDetailViewModel: GroupDetailViewModel): ViewModel
}