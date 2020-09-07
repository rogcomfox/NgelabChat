package com.nusantarian.ngelabpremiumchat.fragment

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nusantarian.ngelabpremiumchat.R
import com.nusantarian.ngelabpremiumchat.databinding.FragmentRegisterBinding
import com.nusantarian.ngelabpremiumchat.model.User

class RegisterFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        binding.btnRegister.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_register) {
            binding.progress.visibility = View.VISIBLE

            val email = binding.tilEmail.editText?.text.toString()
            val name = binding.tilName.editText?.text.toString()
            val phone = binding.tilPhone.editText?.text.toString()
            val school = binding.tilSchool.editText?.text.toString()
            val pass = binding.tilPass.editText?.text.toString()
            val confirm = binding.tilConfirmPass.editText?.text.toString()

            if (!isValid(email, name, phone, school, pass, confirm)) {
                binding.progress.visibility = View.GONE
            } else {
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful){
                        val uid = auth.currentUser?.uid!!
                        pushData(email, name, phone, school, uid)
                    } else {
                        Toast.makeText(activity, R.string.text_failed_register, Toast.LENGTH_LONG).show()
                        Log.e("AUTH", "Failed Create User")
                        binding.progress.visibility = View.GONE
                    }
                }.addOnFailureListener {
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    binding.progress.visibility = View.GONE
                }
            }
        }
    }

    private fun pushData(email: String, name: String, phone: String, school: String, uid: String){
        val reference = FirebaseFirestore.getInstance().collection("users").document(uid)
        val user = User(email, name, phone, school)
        reference.set(user).addOnCompleteListener {
            if (it.isSuccessful){
                auth.signOut()
                Toast.makeText(activity, R.string.text_please_login, Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            } else {
                Toast.makeText(activity, R.string.text_failed_register, Toast.LENGTH_LONG).show()
                Log.e("AUTH", "Failed Push Data")
            }
            binding.progress.visibility = View.GONE
        }.addOnFailureListener {
            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
            binding.progress.visibility = View.GONE
        }
    }

    private fun isValid(
        email: String,
        name: String,
        phone: String,
        school: String,
        pass: String,
        confirm: String
    ): Boolean {
        val empty = activity?.resources?.getString(R.string.text_empty_field)
        val invalid = activity?.resources?.getString(R.string.text_invalid_email)
        val notMatch = activity?.resources?.getString(R.string.text_pass_didnt_match)
        return if (email.isEmpty() || name.isEmpty() || phone.isEmpty() || school.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            binding.tilEmail.error = empty
            binding.tilName.error = empty
            binding.tilSchool.error = empty
            binding.tilPhone.error = empty
            binding.tilPass.error = empty
            binding.tilConfirmPass.error = empty
            false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.tilEmail.error = invalid
            false
        }
        else if (pass != confirm){
            binding.tilPass.error = notMatch
            binding.tilConfirmPass.error = notMatch
            false
        } else {
            binding.tilEmail.error = null
            binding.tilName.error = null
            binding.tilSchool.error = null
            binding.tilPhone.error = null
            binding.tilPass.error = null
            binding.tilConfirmPass.error = null

            binding.tilEmail.isErrorEnabled
            binding.tilName.isErrorEnabled
            binding.tilSchool.isErrorEnabled
            binding.tilPhone.isErrorEnabled
            binding.tilPass.isErrorEnabled
            binding.tilConfirmPass.isErrorEnabled
            true
        }
    }
}