package com.nusantarian.ngelabpremiumchat.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nusantarian.ngelabpremiumchat.R
import com.nusantarian.ngelabpremiumchat.activity.AuthActivity
import com.nusantarian.ngelabpremiumchat.databinding.FragmentMainBinding

class MainFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DocumentReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid!!
        reference = FirebaseFirestore.getInstance().collection("users").document(uid)
        loadData()
        binding.btnSignOut.setOnClickListener(this)
        return binding.root
    }

    private fun loadData(){
        reference.get().addOnSuccessListener {
            val name = it.getString("name")
            binding.tvName.text = "Hi, $name"
        }.addOnFailureListener {
            Log.e("TAG", it.toString())
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_sign_out){
            auth.signOut()
            startActivity(Intent(activity, AuthActivity::class.java))
            activity?.finish()
        }
    }
}