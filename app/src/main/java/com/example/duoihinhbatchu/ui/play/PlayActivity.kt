package com.example.duoihinhbatchu.ui.play

import com.example.duoihinhbatchu.databinding.ActivityPlayBinding
import com.example.duoihinhbatchu.base.BaseActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.duoihinhbatchu.R

class PlayActivity : BaseActivity<ActivityPlayBinding>() {

    override fun createBinding() = ActivityPlayBinding.inflate(layoutInflater)

    override val context = this
}