package com.nusantarian.ngelabpremiumchat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nusantarian.ngelabpremiumchat.R
import com.nusantarian.ngelabpremiumchat.databinding.FragmentConfirmResetBinding

class ConfirmResetFragment : Fragment() {

    private var _binding: FragmentConfirmResetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentConfirmResetBinding.inflate(inflater, container, false)
        return binding.root
    }
}