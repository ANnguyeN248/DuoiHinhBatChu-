package com.example.duoihinhbatchu.ui.play.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.duoihinhbatchu.R
import com.example.duoihinhbatchu.databinding.ItemButtonBinding
import com.example.duoihinhbatchu.domain.Button
import com.example.duoihinhbatchu.domain.Question


@SuppressLint("NotifyDataSetChanged")
class SelectButtonAdapter(private val onItemClicked: ((Button) -> Unit)? = null) : RecyclerView.Adapter<SelectButtonAdapter.ButtonViewHolder>() {

    private val items = mutableListOf<Button>()
    private var question: Question? = null
    private var count = 0


    fun setItems(items: List<Button>, question: Question) {
        this.items.clear()
        this.items.addAll(items)
        this.question = question
        this.count = question.answer.length
        notifyItemRangeChanged(0, items.size)
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
        binding.layoutButton.visibility = View.VISIBLE
        val button = items[position]
        Glide.with(binding.root)
            .load(R.drawable.ic_tile_hover)
            .into(binding.imgBackground)
        binding.tvBody.text = button.body

        binding.layoutButton.setOnClickListener {
            if (count > 0) {
                onItemClicked?.invoke(button)
                binding.layoutButton.visibility = View.INVISIBLE
                count -= 1
            }
        }
    }

    override fun getItemCount() = items.size
}