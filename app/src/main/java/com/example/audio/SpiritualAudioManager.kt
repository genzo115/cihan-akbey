package com.example.audio

import android.content.Context
import android.media.MediaPlayer
import com.example.R

class SpiritualAudioManager(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    private var isEnabled: Boolean = true
    private var isForeground: Boolean = true

    fun startIfAllowed() {
        if (!isEnabled || !isForeground) return
        runCatching {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(context, R.raw.spiritual_ambient)?.apply {
                    isLooping = true
                    setVolume(0.42f, 0.42f)
                }
            }
            if (mediaPlayer?.isPlaying == false) {
                mediaPlayer?.start()
            }
        }
    }

    fun setSoundEnabled(enabled: Boolean) {
        isEnabled = enabled
        if (enabled) {
            startIfAllowed()
        } else {
            pause()
        }
    }

    fun onForegroundChanged(foreground: Boolean) {
        isForeground = foreground
        if (foreground) {
            startIfAllowed()
        } else {
            pause()
        }
    }

    fun pause() {
        runCatching {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        }
    }

    fun release() {
        runCatching {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }
        mediaPlayer = null
    }
}
