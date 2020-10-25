package com.e.tbreview.di.main

import androidx.lifecycle.ViewModel
import com.e.tbreview.di.ViewModelKey
import com.e.tbreview.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindBlogViewModel(mainViewModel: MainViewModel): ViewModel

}