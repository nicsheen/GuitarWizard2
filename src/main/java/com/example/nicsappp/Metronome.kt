package com.example.nicsappp

import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

interface MetronomeListener {

    public fun onBeat( beatNum : Int,  beatsInBar : Int)
}

class Metronome (val bmp: Long, val timeSignatureTop: Int, val timeSignaureBottom : Int = 4) : TimerTask(){


    private var listeners = mutableListOf<MetronomeListener>()
    private var timer = Executors.newSingleThreadScheduledExecutor()
    private var started = false
    private var beatNumber = 0
    var beatsInBar = 4

    public fun addListener (newListener: MetronomeListener){

            listeners.add(newListener)
    }

    public fun removeListener (listener: MetronomeListener){

        var i = 0
        for (l in listeners){
            if (l == listener){
                listeners.removeAt(i)
                return
            }
            i++
        }


    }

    public fun start() : Boolean{

        if (started)
            return false

        when (timeSignaureBottom){

            4-> beatsInBar = timeSignatureTop
            8-> beatsInBar = timeSignatureTop/3
            else -> beatsInBar = timeSignatureTop
        }

        beatNumber = 0
        val period : Long = 60L*1000/bmp            // work out the delay between each beat
        this.timer = Executors.newSingleThreadScheduledExecutor()
        this.timer.scheduleAtFixedRate(
            this,
            0,
             period,
             TimeUnit.MILLISECONDS
        )

        started = true
        return started
    }

    public override fun run (){
        beat()
    }

    private fun beat()  {

        beatNumber++

        for (l in listeners){
            l.onBeat(beatNumber, beatsInBar )
        }
        beatNumber = beatNumber.rem(beatsInBar)

    }

    public fun stop(){

        if (started){
          //  timer.cancel()
           timer.shutdown()
            started = false
      }
    }

    public fun isRunning () : Boolean{

        return started
    }
}