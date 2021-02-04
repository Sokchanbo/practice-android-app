package com.example.practiceapp.features.job

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practiceapp.R
import com.example.practiceapp.databinding.FragmentGithubJobSearchBinding
import com.example.practiceapp.extension.clipToStatusBar
import com.example.practiceapp.extension.hideKeyboard
import com.example.practiceapp.extension.loadMore
import com.example.practiceapp.features.BaseFragment
import com.example.practiceapp.network.response.ApiResponse

class GithubJobSearchFragment : BaseFragment() {

    private var _binding: FragmentGithubJobSearchBinding? = null
    private val binding: FragmentGithubJobSearchBinding
        get() = _binding!!

    private val actionBar: ActionBar?
        get() = (activity as? AppCompatActivity)?.supportActionBar

    private val viewModel: GithubJobSearchViewModel by viewModels { viewModelFactory }

    private val adapter: GithubJobSearchAdapter by lazy {
        GithubJobSearchAdapter {
            // findNavController().navigate(R.id.testFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentGithubJobSearchBinding.inflate(inflater).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializedViewBinding(view)
        setupToolbar()
        setupRecyclerView()
        setupSearch()
        observeLiveData()
    }

    private fun initializedViewBinding(view: View) {
        _binding = FragmentGithubJobSearchBinding.bind(view)
    }

    private fun setupToolbar() {
        binding.toolbar.clipToStatusBar()
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
        actionBar?.setTitle(R.string.github_job_search)
    }

    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = this@GithubJobSearchFragment.adapter
            loadMore {
                viewModel.loadMore()
            }
        }
    }

    private fun setupSearch() {
        binding.layoutSearch.etxSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                activity?.hideKeyboard()
                val title = binding.layoutSearch.etxSearch.text.toString().trim()
                viewModel.setJobTitle(title)
                return@setOnEditorActionListener true
            }
            false
        }

        binding.layoutSearch.etxSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                activity?.hideKeyboard()
                val title = binding.layoutSearch.etxSearch.text.toString().trim()
                viewModel.setJobTitle(title)
                return@setOnKeyListener true
            }
            false
        }
    }

    private fun observeLiveData() {
        viewModel.searchJobLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading ->
                    showProgress()

                is ApiResponse.Success -> {
                    hideProgress()
                    adapter.jobs = response.data
                }

                is ApiResponse.Failed ->
                    hideProgress()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
