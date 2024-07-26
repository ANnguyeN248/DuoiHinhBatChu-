package com.example.duoihinhbatchu.ui.play

import androidx.lifecycle.ViewModel
import com.example.duoihinhbatchu.R
import com.example.duoihinhbatchu.domain.Question
import com.example.duoihinhbatchu.utils.HEART
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class PlayViewModel : ViewModel() {

    private val listQuestions = mutableListOf<Question>()

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> get() = _score.asStateFlow()

    private val _heart = MutableStateFlow(HEART)
    val heart: StateFlow<Int> get() = _heart.asStateFlow()


    fun initQuestions() {
        listQuestions.addAll(
            listOf(
                Question(R.drawable.aomua, "AOMUA"),
                Question(R.drawable.baocao, "BAOCAO"),
                Question(R.drawable.canthiep, "CANTHIEP"),
                Question(R.drawable.cattuong, "CATTUONG"),
                Question(R.drawable.chieutre, "CHIEUTRE"),
                Question(R.drawable.danhlua, "DANHLUA"),
                Question(R.drawable.danong, "DANONG"),
                Question(R.drawable.giandiep, "GIANDIEP"),
                Question(R.drawable.giangmai, "GIANGMAI"),
                Question(R.drawable.hoidong, "HOIDONG"),
                Question(R.drawable.hongtam, "HONGTAM"),
                Question(R.drawable.khoailang, "KHOAILANG"),
                Question(R.drawable.kiemchuyen, "KIEMCHUYEN"),
                Question(R.drawable.lancan, "LANCAN"),
                Question(R.drawable.masat, "MASAT"),
                Question(R.drawable.nambancau, "NAMBANCAU"),
                Question(R.drawable.oto, "OTO"),
                Question(R.drawable.quyhang, "QUYHANG"),
                Question(R.drawable.songsong, "SONGSONG"),
                Question(R.drawable.thattinh, "THATTINH"),
                Question(R.drawable.thothe, "THOTHE"),
                Question(R.drawable.tichphan, "TICHPHAN"),
                Question(R.drawable.tohoai, "TOHOAI"),
                Question(R.drawable.totien, "TOTIEN"),
                Question(R.drawable.tranhthu, "TRANHTHU"),
                Question(R.drawable.vuaphaluoi, "VUAPHALUOI"),
                Question(R.drawable.vuonbachthu, "VUONBACHTHU"),
                Question(R.drawable.xakep, "XAKEP"),
                Question(R.drawable.xaphong, "XAPHONG"),
                Question(R.drawable.xedapdien, "XEDAPDIEN")
            )
        )
    }

    fun getRandomQuestion(): Question? {
        if (listQuestions.isEmpty()) return null
        val randomIndex = Random.nextInt(listQuestions.size)
        val randomQuestion = listQuestions[randomIndex]
        listQuestions.removeAt(randomIndex)
        return randomQuestion
    }

    fun isGameFinished() = listQuestions.isEmpty()

    fun addScore() {
        _score.value += 500
    }

    fun decreaseHeart() {
        _heart.value -= 1
    }

    fun restartGame() {
        _score.value = 0
        _heart.value = HEART
        listQuestions.clear()
        initQuestions()
    }
}