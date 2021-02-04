package com.example.practiceapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practiceapp.features.ViewModelFactory
import com.example.practiceapp.features.job.GithubJobSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(GithubJobSearchViewModel::class)
    abstract fun bindGithubJobSearchViewModel(viewModel: GithubJobSearchViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
