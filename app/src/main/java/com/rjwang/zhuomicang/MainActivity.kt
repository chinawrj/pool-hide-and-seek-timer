package com.rjwang.zhuomicang

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.rjwang.zhuomicang.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: SharedPreferences
    private var countDownTimer: CountDownTimer? = null
    private var soundTimer: CountDownTimer? = null
    private var testMediaPlayer: MediaPlayer? = null
    private var mediaPlayer: MediaPlayer? = null
    
    private var intervalSeconds = 60 // 提醒间隔，默认60秒
    private var durationSeconds = 10 // 提醒持续时间，默认10秒
    private var isTimerRunning = false
    private var isPlayingSound = false
    private var selectedRingtoneUri: Uri? = null
    private var selectedRingtoneName: String = "默认通知音"
    
    companion object {
        private const val RINGTONE_PICKER_REQUEST_CODE = 999
        private const val PREFS_NAME = "TimerAppPrefs"
        private const val PREF_RINGTONE_URI = "ringtone_uri"
        private const val PREF_RINGTONE_NAME = "ringtone_name"
        private const val PREF_INTERVAL = "interval_seconds"
        private const val PREF_DURATION = "duration_seconds"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        loadPreferences()
        setupViews()
        setupClickListeners()
        initializeMediaPlayer()
    }
    
    private fun loadPreferences() {
        intervalSeconds = prefs.getInt(PREF_INTERVAL, 60)
        durationSeconds = prefs.getInt(PREF_DURATION, 10)
        
        val uriString = prefs.getString(PREF_RINGTONE_URI, null)
        if (!uriString.isNullOrEmpty()) {
            selectedRingtoneUri = Uri.parse(uriString)
        }
        selectedRingtoneName = prefs.getString(PREF_RINGTONE_NAME, "默认通知音") ?: "默认通知音"
    }
    
    private fun savePreferences() {
        prefs.edit().apply {
            putInt(PREF_INTERVAL, intervalSeconds)
            putInt(PREF_DURATION, durationSeconds)
            putString(PREF_RINGTONE_URI, selectedRingtoneUri?.toString())
            putString(PREF_RINGTONE_NAME, selectedRingtoneName)
            apply()
        }
    }
    
    private fun setupViews() {
        updateIntervalDisplay()
        updateDurationDisplay()
        updateCountdownDisplay(intervalSeconds)
        updateStatus("等待开始")
        updateRingtoneDisplay()
    }
    
    private fun setupClickListeners() {
        // 间隔时间调整
        binding.btnDecreaseInterval.setOnClickListener {
            if (intervalSeconds > 5) {
                intervalSeconds -= 5
                updateIntervalDisplay()
                savePreferences()
                if (!isTimerRunning) {
                    updateCountdownDisplay(intervalSeconds)
                }
            }
        }
        
        binding.btnIncreaseInterval.setOnClickListener {
            if (intervalSeconds < 300) { // 最大5分钟
                intervalSeconds += 5
                updateIntervalDisplay()
                savePreferences()
                if (!isTimerRunning) {
                    updateCountdownDisplay(intervalSeconds)
                }
            }
        }
        
        // 持续时间调整
        binding.btnDecreaseDuration.setOnClickListener {
            if (durationSeconds > 1) {
                durationSeconds -= 1
                updateDurationDisplay()
                savePreferences()
            }
        }
        
        binding.btnIncreaseDuration.setOnClickListener {
            if (durationSeconds < 60) { // 最大60秒
                durationSeconds += 1
                updateDurationDisplay()
                savePreferences()
            }
        }
        
        // 提示音选择
        binding.btnSelectRingtone.setOnClickListener {
            openRingtonePicker()
        }
        
        binding.btnTestRingtone.setOnClickListener {
            testCurrentRingtone()
        }
        
        // 开始按钮
        binding.btnStart.setOnClickListener {
            if (!isTimerRunning) {
                startTimer()
            }
        }
        
        // 停止按钮
        binding.btnStop.setOnClickListener {
            if (isTimerRunning) {
                stopTimer()
            }
        }
    }
    
    private fun initializeMediaPlayer() {
        updateMediaPlayer()
    }
    
    private fun updateMediaPlayer() {
        try {
            mediaPlayer?.release()
            val uri = selectedRingtoneUri ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            mediaPlayer = MediaPlayer.create(this, uri)
            mediaPlayer?.isLooping = true
        } catch (e: Exception) {
            e.printStackTrace()
            showMessage("无法初始化声音播放器")
        }
    }
    
    private fun openRingtonePicker() {
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
            putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION)
            putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "选择提醒音")
            putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, selectedRingtoneUri)
            putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
            putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false)
        }
        
        try {
            startActivityForResult(intent, RINGTONE_PICKER_REQUEST_CODE)
        } catch (e: Exception) {
            showMessage("无法打开铃声选择器")
        }
    }
    
    private fun testCurrentRingtone() {
        if (testMediaPlayer?.isPlaying == true) {
            stopTestRingtone()
            return
        }
        
        try {
            val uri = selectedRingtoneUri ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            testMediaPlayer = MediaPlayer.create(this, uri)
            testMediaPlayer?.start()
            
            binding.btnTestRingtone.text = "停止"
            
            // 3秒后自动停止试听
            Handler(Looper.getMainLooper()).postDelayed({
                stopTestRingtone()
            }, 3000)
            
        } catch (e: Exception) {
            e.printStackTrace()
            showMessage("无法播放提示音")
        }
    }
    
    private fun stopTestRingtone() {
        try {
            testMediaPlayer?.stop()
            testMediaPlayer?.release()
            testMediaPlayer = null
            binding.btnTestRingtone.text = "试听"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (requestCode == RINGTONE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val uri = data?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            selectedRingtoneUri = uri
            
            // 获取铃声名称
            selectedRingtoneName = if (uri != null) {
                try {
                    val ringtone = RingtoneManager.getRingtone(this, uri)
                    ringtone?.getTitle(this) ?: "自定义铃声"
                } catch (e: Exception) {
                    "自定义铃声"
                }
            } else {
                "默认通知音"
            }
            
            updateRingtoneDisplay()
            updateMediaPlayer()
            savePreferences()
            showMessage("提示音已更换")
        }
    }
    
    private fun startTimer() {
        isTimerRunning = true
        updateStatus("定时器运行中...")
        updateButtonStates()
        
        countDownTimer = object : CountDownTimer((intervalSeconds * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = (millisUntilFinished / 1000).toInt()
                updateCountdownDisplay(secondsLeft)
            }
            
            override fun onFinish() {
                playNotificationSound()
                // 重新启动定时器
                Handler(Looper.getMainLooper()).postDelayed({
                    if (isTimerRunning) {
                        startTimer()
                    }
                }, 100)
            }
        }
        countDownTimer?.start()
    }
    
    private fun stopTimer() {
        isTimerRunning = false
        countDownTimer?.cancel()
        soundTimer?.cancel()
        stopNotificationSound()
        
        updateStatus("已停止")
        updateCountdownDisplay(intervalSeconds)
        updateButtonStates()
    }
    
    private fun playNotificationSound() {
        if (isPlayingSound) return
        
        isPlayingSound = true
        updateStatus("正在提醒中...")
        
        // 播放声音
        try {
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        // 设置声音播放持续时间
        soundTimer = object : CountDownTimer((durationSeconds * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = (millisUntilFinished / 1000).toInt()
                updateStatus("提醒中... $secondsLeft 秒")
            }
            
            override fun onFinish() {
                stopNotificationSound()
                if (isTimerRunning) {
                    updateStatus("定时器运行中...")
                }
            }
        }
        soundTimer?.start()
    }
    
    private fun stopNotificationSound() {
        isPlayingSound = false
        soundTimer?.cancel()
        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                mediaPlayer?.seekTo(0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun updateIntervalDisplay() {
        binding.tvInterval.text = intervalSeconds.toString()
    }
    
    private fun updateDurationDisplay() {
        binding.tvDuration.text = durationSeconds.toString()
    }
    
    private fun updateCountdownDisplay(seconds: Int) {
        binding.tvCountdown.text = seconds.toString()
    }
    
    private fun updateStatus(status: String) {
        binding.tvStatus.text = status
    }
    
    private fun updateRingtoneDisplay() {
        binding.tvCurrentRingtone.text = selectedRingtoneName
    }
    
    private fun updateButtonStates() {
        binding.btnStart.isEnabled = !isTimerRunning
        binding.btnStop.isEnabled = isTimerRunning
        
        // 禁用设置按钮当定时器运行时
        val settingsEnabled = !isTimerRunning
        binding.btnDecreaseInterval.isEnabled = settingsEnabled
        binding.btnIncreaseInterval.isEnabled = settingsEnabled
        binding.btnDecreaseDuration.isEnabled = settingsEnabled
        binding.btnIncreaseDuration.isEnabled = settingsEnabled
        binding.btnSelectRingtone.isEnabled = settingsEnabled
        binding.btnTestRingtone.isEnabled = settingsEnabled
    }
    
    private fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
        stopTestRingtone()
        mediaPlayer?.release()
        mediaPlayer = null
    }
    
    override fun onPause() {
        super.onPause()
        // 当应用进入后台时，保持定时器运行但可以选择停止声音
    }
}