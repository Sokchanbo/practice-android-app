package com.example.practiceapp.di

import com.example.practiceapp.features.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
