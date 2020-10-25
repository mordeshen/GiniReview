package com.e.tbreview.di


import com.e.tbreview.di.main.MainFragmentBuildersModule
import com.e.tbreview.di.main.MainModule
import com.e.tbreview.di.main.MainScope
import com.e.tbreview.di.main.MainViewModelModule
import com.e.tbreview.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {


    @MainScope
    @ContributesAndroidInjector(
        modules = [MainModule::class, MainFragmentBuildersModule::class, MainViewModelModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity

}