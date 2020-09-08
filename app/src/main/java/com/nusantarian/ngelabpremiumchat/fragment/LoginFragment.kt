package com.nusantarian.ngelabpremiumchat.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.nusantarian.ngelabpremiumchat.R
import com.nusantarian.ngelabpremiumchat.activity.MainActivity
import com.nusantarian.ngelabpremiumchat.databinding.FragmentLoginBinding

class LoginFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var ft: FragmentTransaction

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        ft = activity?.supportFragmentManager?.beginTransaction()!!
        binding.btnLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
        binding.tvForgotPassword.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> {
                binding.progress.visibility = View.VISIBLE
                val email = binding.tilEmail.editText?.text.toString()
                val pass = binding.tilPass.editText?.text.toString()
                if (!isValid(email, pass)) {
                    binding.progress.visibility = View.GONE
                } else {
                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(Intent(activity, MainActivity::class.java))
                            activity?.overridePendingTransition(
                                android.R.anim.fade_in,
                                android.R.anim.fade_out
                            )
                            activity?.finish()
                        } else {
                            Toast.makeText(activity, R.string.text_failed_login, Toast.LENGTH_LONG)
                                .show()
                        }
                        binding.progress.visibility = View.GONE
                    }.addOnFailureListener {
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                        binding.progress.visibility = View.GONE
                    }
                }
            }
            R.id.tv_forgot_password ->
                ft.replace(R.id.frame_auth, ForgotPassFragment())
                    .addToBackStack(null)
                    .commit()
            R.id.btn_register ->
                ft.replace(R.id.frame_auth, RegisterFragment())
                    .addToBackStack(null)
                    .commit()
        }
    }

    private fun isValid(email: String, pass: String): Boolean {
        val empty = activity?.resources?.getString(R.string.text_empty_field)
        val invalid = activity?.resources?.getString(R.string.text_invalid_email)

        return if (email.isEmpty()) {
            binding.tilEmail.error = empty
            false
        } else if (pass.isEmpty()) {
            binding.tilPass.error = empty
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = invalid
            false
        } else {
            binding.tilEmail.error = null
            binding.tilPass.error = null

            binding.tilEmail.isErrorEnabled
            binding.tilPass.isErrorEnabled
            true
        }
    }
}