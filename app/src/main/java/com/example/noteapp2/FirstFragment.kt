package com.example.noteapp2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.noteapp2.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

private lateinit var binding: FragmentFirstBinding
private val adapter = VPAdapter(this::onClick)

    private fun onClick() {
        findNavController().navigate(R.id.secondFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vp.adapter = adapter
        binding.ci.setViewPager(binding.vp)

    }
}