package com.example.practiceapp.features.job

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practiceapp.databinding.HolderJobSearchResultBinding
import com.example.practiceapp.network.response.JobResponse

class GithubJobSearchAdapter(private val onItemClicked: () -> Unit) :
    RecyclerView.Adapter<GithubJobSearchAdapter.GithubJobSearchViewHolder>() {

    var jobs = listOf<JobResponse>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubJobSearchViewHolder =
        GithubJobSearchViewHolder(
            HolderJobSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: GithubJobSearchViewHolder, position: Int) {
        holder.bindData()
    }

    override fun getItemCount(): Int = jobs.size

    inner class GithubJobSearchViewHolder(private val binding: HolderJobSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { onItemClicked.invoke() }
        }

        fun bindData() {
            with(jobs[adapterPosition]) {
                Glide.with(binding.imgCompanyLogo)
                    .load(companyLogo)
                    .circleCrop()
                    .into(binding.imgCompanyLogo)
                binding.tvJobTitle.text = title
                binding.tvCompanyName.text = company
                binding.tvLocation.text = location
                binding.tvJobType.text = type
                binding.tvCreatedAt.text = createdAt
            }
        }
    }
}
