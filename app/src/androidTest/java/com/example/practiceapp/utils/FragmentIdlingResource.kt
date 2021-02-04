package com.example.practiceapp.utils

import androidx.fragment.app.FragmentManager
import androidx.test.espresso.IdlingResource

class FragmentIdlingResource(
    private val manager: FragmentManager,
    private val tag: String?
) : IdlingResource {
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName() = "fragment idling resource"

    override fun registerIdleTransitionCallback(
        callback: IdlingResource.ResourceCallback?
    ) {
        resourceCallback = callback
    }

    override fun isIdleNow(): Boolean {
        val idle = (manager.findFragmentByTag(tag) == null)
        if (idle) {
            resourceCallback?.onTransitionToIdle()
        }
        return idle
    }
}
