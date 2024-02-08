package com.example.mytestapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.mytestapplication.databinding.LayoutActivityMainBinding

class MainActivity : ComponentActivity() {

    private lateinit var binding: LayoutActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}