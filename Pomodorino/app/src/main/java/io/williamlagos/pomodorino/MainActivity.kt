package io.williamlagos.pomodorino

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.williamlagos.pomodorino.databinding.ActivityMainBinding
import java.util.*

internal class Helper : TimerTask() {

    override fun run() {
        Log.i("Timer", "Timer ran " + ++i)
    }

    companion object {
        var i = 0
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.timerLabel.setText("Timer started")
        var t = Timer(true)
        t.schedule(Helper(), 1000, 5000)
    }
}