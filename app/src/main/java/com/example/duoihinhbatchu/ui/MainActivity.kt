package com.example.duoihinhbatchu.ui

import android.content.Intent
import com.example.duoihinhbatchu.databinding.ActivityMainBinding
import com.example.duoihinhbatchu.ui.play.PlayActivity
import com.example.duoihinhbatchu.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override val context = this

    override fun setUp() {
        super.setUp()

        binding.tvStartGame.setOnClickListener {
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
        }
    }
}