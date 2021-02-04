package com.example.practiceapp.common.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.practiceapp.databinding.DialogFragmentProgressBinding

class ProgressDialogFragment(
    private val cancelable: Boolean = false,
    private val dimAmount: Float = 0f
) : DialogFragment() {

    companion object {
        fun newInstance(
            cancelable: Boolean = false,
            dimAmount: Float = 0f
        ): ProgressDialogFragment = ProgressDialogFragment(cancelable, dimAmount)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DialogFragmentProgressBinding.inflate(inflater, container, false).root

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = cancelable
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.let {
            it.setDimAmount(dimAmount)
            it.setBackgroundDrawableResource(android.R.color.transparent)
        }
        return dialog
    }
}
