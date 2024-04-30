package com.example.noteapp2.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.noteapp2.R
import com.example.noteapp2.databinding.ItemOnboardingBinding
import com.example.noteapp2.models.OnBoarding

class VPAdapter(private val onClick: () -> Unit) : Adapter<VPAdapter.VPViewHolder>() {
    private val list = arrayListOf<OnBoarding>(
        OnBoarding("Очень удобный функционал", R.raw.note1),
        OnBoarding("Быстрый, качественный продукт", R.raw.note2),
        OnBoarding("Куча функций и интересных фишек", R.raw.note3),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VPViewHolder {
        return VPViewHolder(
            ItemOnboardingBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )

        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VPViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class VPViewHolder(private val binding: ItemOnboardingBinding) :
        ViewHolder(binding.root) {
        fun bind(onBoarding: OnBoarding) {
            binding.tvTitle.text = onBoarding.title

            binding.tvSkip.isVisible = adapterPosition != list.lastIndex
            binding.btnGetStarted.isVisible = adapterPosition == list.lastIndex

            onBoarding.img?.let {
                binding.ivBoard.setAnimation(it)
                binding.ivBoard.playAnimation()
            }

            binding.tvSkip.setOnClickListener {
                onClick()
            }
            binding.btnGetStarted.setOnClickListener {
                onClick()
            }
        }
    }
}