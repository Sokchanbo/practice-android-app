package com.example.practiceapp.di

import com.example.practiceapp.features.job.GithubJobSearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeGithubJobSearchFragment(): GithubJobSearchFragment
}
