package com.example.nicsappp

import android.app.Activity
import android.media.MediaPlayer

class MetromomeSimpleSoundView (context : Activity, resId : Int) {

    var mediaPlayer : MediaPlayer
    var ready : Boolean = false
    var playing : Boolean  = false

    init {

            mediaPlayer = MediaPlayer.create(context, R.raw.snare)
            mediaPlayer.setOnPreparedListener{
                ready = true
            }
            mediaPlayer.setOnCompletionListener { mediaPlayer.seekTo(0)
                playing = false
            }

    }



    public fun play(){

        if (ready){
            mediaPlayer.start()
            playing = true
        }

    }

    public fun stop (){

        if (playing){
            mediaPlayer.stop()
            playing = false
            mediaPlayer.seekTo(0)
        }

    }


}