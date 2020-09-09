package com.nusantarian.ngelabpremiumchat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.nusantarian.ngelabpremiumchat.R
import com.nusantarian.ngelabpremiumchat.databinding.FragmentLandingBinding


class LandingFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLandingBinding? = null
    private val binding get() = _binding!!
    private lateinit var ft: FragmentTransaction

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLandingBinding.inflate(inflater, container, false)
        binding.btnLogin.setOnClickListener(this)
        binding.tvSignUp.setOnClickListener(this)
        binding.btnGoogle.setOnClickListener(this)
        ft = activity?.supportFragmentManager?.beginTransaction()!!
        return binding.root
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_login ->
                ft.replace(R.id.frame_auth, LoginFragment())
                    .addToBackStack(null)
                    .commit()
            R.id.btn_google ->
                Toast.makeText(activity, R.string.text_under_construction, Toast.LENGTH_LONG).show()
            R.id.tv_sign_up ->
                ft.replace(R.id.frame_auth, RegisterFragment())
                    .addToBackStack(null)
                    .commit()
        }
    }
}