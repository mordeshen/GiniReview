package com.e.tbreview.di.main

import com.e.tbreview.ui.main.MainFragment
import com.e.tbreview.ui.main.TaboolaWidgetFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector()
    abstract fun contributetbFragment(): TaboolaWidgetFragment

}