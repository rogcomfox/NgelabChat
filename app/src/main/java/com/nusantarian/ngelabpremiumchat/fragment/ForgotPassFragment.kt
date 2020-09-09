package com.nusantarian.ngelabpremiumchat.fragment

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.nusantarian.ngelabpremiumchat.R
import com.nusantarian.ngelabpremiumchat.databinding.FragmentForgotPassBinding

class ForgotPassFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentForgotPassBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var ft: FragmentTransaction

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgotPassBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        ft = activity?.supportFragmentManager?.beginTransaction()!!
        binding.btnResetPassword.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_reset_password){
            binding.progress.visibility = View.VISIBLE
            val email = binding.tilEmail.editText?.text.toString()
            if (!isValid(email)){
                binding.progress.visibility = View.GONE
            } else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful){
                        ft.replace(R.id.frame_auth, ConfirmResetFragment())
                            .commit()
                    } else {
                        Toast.makeText(activity, R.string.text_failed_sent_reset, Toast.LENGTH_LONG).show()
                    }
                    binding.progress.visibility = View.GONE
                }.addOnFailureListener {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun isValid(email: String) : Boolean {
        val empty = activity?.resources?.getString(R.string.text_empty_field)
        val invalid = activity?.resources?.getString(R.string.text_invalid_email)
        return if (email.isEmpty()){
            binding.tilEmail.error = empty
            false
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.tilEmail.error = invalid
            false
        } else {
            binding.tilEmail.error = null
            binding.tilEmail.isErrorEnabled
            true
        }
    }
}