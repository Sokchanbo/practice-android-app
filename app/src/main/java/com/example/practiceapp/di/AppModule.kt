package com.example.practiceapp.di

import dagger.Module
import dagger.android.AndroidInjectionModule

@Module(includes = [
    AndroidInjectionModule::class,
    ActivityBuildersModule::class,
    FragmentBuildersModule::class,
    NetworkModule::class,
    ViewModelModule::class
])
class AppModule
