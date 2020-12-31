package io.williamlagos.pomodorino

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import io.williamlagos.pomodorino.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var counter = 0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pomodoroMillis = 1_500_000L
        val timer = object: CountDownTimer(pomodoroMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var secondsUntilFinished = millisUntilFinished / 1000
                binding.timerLabel.text = "Timer: $secondsUntilFinished."
            }

            override fun onFinish() {
                binding.timerLabel.text = "Timer ran out."
            }
        }
        timer.start()
    }
}