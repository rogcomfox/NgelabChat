package com.nusantarian.ngelabpremiumchat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nusantarian.ngelabpremiumchat.R
import com.nusantarian.ngelabpremiumchat.databinding.FragmentForgotPassBinding

class ForgotPassFragment : Fragment() {

    private var _binding: FragmentForgotPassBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgotPassBinding.inflate(inflater, container, false)
        return binding.root
    }
}