package com.example.duoihinhbatchu.ui.play.adapter

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.duoihinhbatchu.R
import com.example.duoihinhbatchu.databinding.ItemButtonBinding
import com.example.duoihinhbatchu.domain.Button
import com.example.duoihinhbatchu.domain.ButtonType
import com.example.duoihinhbatchu.domain.Question


@SuppressLint("NotifyDataSetChanged")
class AnswerButtonAdapter : RecyclerView.Adapter<AnswerButtonAdapter.ButtonViewHolder>() {

    val items = mutableListOf<Button>()
    private var buttonType = ButtonType.BASIC
    private var question: Question? = null
    var position = 0


    fun setItems(items: List<Button>, question: Question) {
        this.items.clear()
        this.items.addAll(items)
        position = 0
        this.question = question
        notifyItemRangeChanged(0, items.size)
    }

    fun changeType(buttonType: ButtonType) {
        this.buttonType = buttonType
        notifyItemRangeChanged(0, items.size)
    }

    fun addAnswerChar(char: String) {
        items[position].body = char
        notifyItemChanged(position)
        position += 1
    }

    fun checkAnswerCorrect(): Boolean {
        var answer = ""
        items.forEach {
            answer += it.body
        }
        if (question?.answer == answer) {
            changeType(ButtonType.CORRECT)
            return true
        } else {
            changeType(ButtonType.INCORRECT)
            return false
        }
    }

    inner class ButtonViewHolder(
        val binding: ItemButtonBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        return ButtonViewHolder(
            ItemButtonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val binding = holder.binding
        val button = items[position]
        val bg = when (buttonType) {
            ButtonType.BASIC -> R.drawable.ic_tile_hover
            ButtonType.CORRECT -> R.drawable.ic_tile_true
            ButtonType.INCORRECT -> R.drawable.ic_tile_false
        }
        Glide.with(binding.root)
            .load(bg)
            .into(binding.imgBackground)
        binding.tvBody.text = button.body
    }

    override fun getItemCount() = items.size
}