package com.example.crosstext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.crosstext.databinding.ActivityContentBinding

class ContentActivity : AppCompatActivity() {

    lateinit var binding:ActivityContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataintent = intent
        val title = dataintent.getStringExtra("title")
        val text = dataintent.getStringExtra("text")

        binding.contentTitle.text = title
        binding.contentText.setText(text,TextView.BufferType.EDITABLE)
    }
}