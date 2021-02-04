package com.example.practiceapp.features

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.practiceapp.common.view.ProgressDialogFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private var _progressDialogFragment: ProgressDialogFragment? = null

    protected fun showProgress() {
        if (_progressDialogFragment == null) {
            _progressDialogFragment = ProgressDialogFragment.newInstance()
        }
        _progressDialogFragment?.show(childFragmentManager, null)
    }

    protected fun hideProgress() {
        _progressDialogFragment?.dismiss()
    }
}
