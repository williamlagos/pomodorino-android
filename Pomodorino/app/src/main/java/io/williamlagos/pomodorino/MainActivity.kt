package io.williamlagos.pomodorino

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import io.williamlagos.pomodorino.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val POMODORO_DURATION = 25 * 60 * 1000L // 25 minutes
        const val SHORT_BREAK_DURATION = 5 * 60 * 1000L // 5 minutes
    }

    private lateinit var binding: ActivityMainBinding
    private var timer: CountDownTimer? = null
    private var isTimerRunning = false
    private var currentPhase = PomodoroPhase.FOCUS
    private var timeLeftInMillis = POMODORO_DURATION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupClickListeners()
        updateTimerUI()
    }

    private fun setupUI() {
        binding.circularProgress.max = 100
        binding.phaseLabel.text = currentPhase.label
        updateControlButtons()
    }

    private fun setupClickListeners() {
        binding.startPauseButton.setOnClickListener {
            if (isTimerRunning) pauseTimer() else startTimer()
        }

        binding.resetButton.setOnClickListener {
            resetTimer()
        }

        binding.skipButton.setOnClickListener {
            skipToNextPhase()
        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerUI()
            }

            override fun onFinish() {
                moveToNextPhase()
            }
        }.start()

        isTimerRunning = true
        updateControlButtons()
    }

    private fun pauseTimer() {
        timer?.cancel()
        isTimerRunning = false
        updateControlButtons()
    }

    private fun resetTimer() {
        timer?.cancel()
        timeLeftInMillis = currentPhase.duration
        isTimerRunning = false
        updateTimerUI()
        updateControlButtons()
    }

    private fun skipToNextPhase() {
        moveToNextPhase()
    }

    private fun moveToNextPhase() {
        currentPhase = when (currentPhase) {
            PomodoroPhase.FOCUS -> PomodoroPhase.SHORT_BREAK
            PomodoroPhase.SHORT_BREAK -> PomodoroPhase.FOCUS
        }
        timeLeftInMillis = currentPhase.duration
        binding.phaseLabel.text = currentPhase.label
        resetTimer()
    }

    private fun updateTimerUI() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        binding.timerLabel.text = String.format("%02d:%02d", minutes, seconds)

        val progress = ((timeLeftInMillis.toFloat() / currentPhase.duration.toFloat()) * 100).toInt()
        binding.circularProgress.setProgress(progress, true)
    }

    private fun updateControlButtons() {
        binding.startPauseButton.setImageResource(
            if (isTimerRunning) R.drawable.ic_pause else R.drawable.ic_play
        )
    }
}

enum class PomodoroPhase(val duration: Long, val label: String) {
    FOCUS(MainActivity.POMODORO_DURATION, "FOCUS TIME"),
    SHORT_BREAK(MainActivity.SHORT_BREAK_DURATION, "SHORT BREAK")
}