package com.nusantarian.ngelabpremiumchat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nusantarian.ngelabpremiumchat.databinding.ActivityNewMessageBinding

class NewMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}