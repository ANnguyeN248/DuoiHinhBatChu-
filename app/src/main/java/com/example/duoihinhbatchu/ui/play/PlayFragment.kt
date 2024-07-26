package com.example.duoihinhbatchu.ui.play

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.duoihinhbatchu.databinding.FragmentPlayBinding
import com.example.duoihinhbatchu.domain.Button
import com.example.duoihinhbatchu.domain.ButtonType
import com.example.duoihinhbatchu.domain.Question
import com.example.duoihinhbatchu.ui.play.adapter.AnswerButtonAdapter
import com.example.duoihinhbatchu.ui.play.adapter.CustomLayoutManager
import com.example.duoihinhbatchu.ui.play.adapter.SelectButtonAdapter
import com.example.duoihinhbatchu.ui.play.dialog.DialogListener
import com.example.duoihinhbatchu.ui.play.dialog.GameOverDialog
import com.example.duoihinhbatchu.ui.play.dialog.VictoryDialog
import com.example.duoihinhbatchu.utils.CORRECT_MESSAGE
import com.example.duoihinhbatchu.utils.INCORRECT_MESSAGE
import com.example.duoihinhbatchu.base.BaseFragment
import kotlinx.coroutines.launch
import kotlin.random.Random

@SuppressLint("SetTextI18n")
class PlayFragment : BaseFragment<FragmentPlayBinding>(), DialogListener {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlayBinding.inflate(inflater, container, false)

    private val viewModel: PlayViewModel by viewModels()

    private lateinit var selectButtonAdapter: SelectButtonAdapter

    private lateinit var answerButtonAdapter: AnswerButtonAdapter

    override fun init() = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()
        initQuestion()
    }

    private fun initQuestion() {
        viewModel.initQuestions()
        nextQuestion()
    }

    private fun setUp() {
        setUpSelectButtons()

        setupAnswerButtons()

        setupEventListener()

        setupEventFlow()
    }

    private fun setupEventFlow() {
        lifecycleScope.launch {
            viewModel.heart.collect {
                if (it <= 0) {
                    GameOverDialog(
                        requireContext(),
                        viewModel.score.value,
                        this@PlayFragment
                    ).show()
                }
                binding.tvHeart.text = it.toString()
            }
        }

        lifecycleScope.launch {
            viewModel.score.collect {
                binding.tvScore.text = it.toString()
            }
        }
    }

    private fun setupEventListener() {
        binding.tvNext.setOnClickListener {
            nextQuestion()
        }
    }

    private fun setupAnswerButtons() {
        answerButtonAdapter = AnswerButtonAdapter()
        binding.rcvAnswerButton.apply {
            adapter = answerButtonAdapter
            layoutManager = CustomLayoutManager(requireContext())
        }
    }

    private fun setUpSelectButtons() {
        selectButtonAdapter = SelectButtonAdapter(
            onItemClicked = {
                onSelectAnswer(it)
            }
        )

        binding.rcvSelectButtons.apply {
            adapter = selectButtonAdapter
            layoutManager = CustomLayoutManager(requireContext())
        }
    }

    private fun onSelectAnswer(button: Button) {
        // add button in answer adapter
        answerButtonAdapter.apply {
            addAnswerChar(button.body)

            // check answer is full or not
            if (position == items.size) {
                binding.tvMessage.visibility = VISIBLE
                binding.tvMessage.text = if (checkAnswerCorrect()) {
                    viewModel.addScore()
                    CORRECT_MESSAGE
                } else {
                    viewModel.decreaseHeart()
                    INCORRECT_MESSAGE
                }

                if (viewModel.isGameFinished() && viewModel.heart.value > 0) {
                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            VictoryDialog(
                                requireContext(),
                                viewModel.score.value,
                                this@PlayFragment
                            ).show()
                        },
                        1000
                    )
                } else {
                    binding.tvNext.visibility = VISIBLE
                }
            }
        }
    }

    private fun nextQuestion() {
        resetUI()
        viewModel.getRandomQuestion()?.let { question ->
            binding.imgQuestion.setImageResource(question.resourceImg)

            selectButtonAdapter.apply {
                setItems(generateSelectButtons(question), question)
            }

            answerButtonAdapter.apply {
                setItems(generateAnswerButtons(question), question)
            }
        }
    }

    private fun generateSelectButtons(question: Question): List<Button> {
        val char = "ABCDEFGHIJKLMNOPQRSTUVXY"
        val list = mutableListOf<Button>()
        question.answer.forEach {
            list.add(Button(it.toString()))
        }
        (1..16 - question.answer.length).map {
            list.add(Button(char[Random.nextInt(char.length)].toString()))
        }
        return list.shuffled()
    }

    private fun generateAnswerButtons(question: Question): List<Button> {
        return (1..question.answer.length).map { Button() }
    }

    private fun resetUI() {
        showResult(false)
        binding.tvMessage.text = ""
        answerButtonAdapter.changeType(ButtonType.BASIC)
    }

    private fun showResult(isShow: Boolean) {
        binding.tvNext.visibility = if (isShow) VISIBLE else INVISIBLE
        binding.tvMessage.visibility = if (isShow) VISIBLE else INVISIBLE
    }

    override fun onRestartGame() {
        viewModel.restartGame()
        resetUI()
        nextQuestion()
    }

    override fun onExitGame() {
        activity?.finish()
    }
}