package com.bignerdranch.android.basketballscore

import android.app.Activity
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.util.Log
import java.lang.Exception

private const val TAG = "SoundManager"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 2

class SoundManager (private val assets: AssetManager) {

    val sounds: List<Sound>
    private val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_SOUNDS)
        .build()



    init {
        sounds = loadSounds()
    }

    fun play (sound: Sound) {
        sound.soundId?.let {
            soundPool.play(it,1.0f, 1.0f, 1, 0, 1.0f)
        }
    }

    fun playSoundPool () {
        play(sounds[0])
    }

    fun loadSounds(): List<Sound> {

        val soundNames: Array<String>

        try {
            soundNames = assets.list(SOUNDS_FOLDER)!!
        } catch (e: Exception) {
            Log.e(TAG, "Could not list assets", e)
            return emptyList()
        }

        val sounds = mutableListOf<Sound>()
        soundNames.forEach { filename ->
            val assetPath = "$SOUNDS_FOLDER/$filename"
            val sound = Sound(assetPath)
            try {
                load(sound)
                sounds.add(sound)
            } catch (ioe: java.io.IOException) {
                Log.e(TAG, "Could not load sound $filename", ioe)
            }
        }
        return sounds
    }


    private fun load (sound: Sound) {
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }


}