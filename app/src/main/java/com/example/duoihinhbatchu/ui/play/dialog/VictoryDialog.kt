package com.example.duoihinhbatchu.ui.play.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.duoihinhbatchu.databinding.DialogVictoryBinding

class VictoryDialog(
    context: Context,
    private val score: Int,
    private val listener: DialogListener
) : Dialog(context) {

    private lateinit var binding: DialogVictoryBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogVictoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setCanceledOnTouchOutside(false)
        setCancelable(false)

        binding.tvScore.text = "Số điểm của bạn là: $score điểm"

        binding.btnRestart.setOnClickListener {
            listener.onRestartGame()
            dismiss()
        }

        binding.btnClose.setOnClickListener {
            listener.onExitGame()
            dismiss()
        }
    }
}