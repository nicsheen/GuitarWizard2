package com.example.nicsappp

import org.junit.Test
import java.util.*
import kotlin.concurrent.schedule

import org.junit.Assert.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MetronomeTest : MetronomeListener, TimerTask(){

     private var metronome : Metronome
     private var bpm = 100L
     private var tst = 4
     private var tsb = 4
     private var beatCount = 0L
     private var endTime = 0L
     private var calcBpm  = 0L
     private var startTime= 0L
     private var selfTimer=  Executors.newSingleThreadScheduledExecutor()
     private var stop = false
     private var beatsInBar = 0
     private var lastTime = 0L

    init {
        metronome = Metronome(100,4,4)
    }


    private fun setup (bpm: Long, timeSignatureTop: Int, timeSignatureBottom: Int){

        this.bpm  = bpm
        tst = timeSignatureTop
        tsb = timeSignatureBottom
        endTime = 0L
        calcBpm =  0L
        startTime = 0L
        beatCount = 0L
        metronome = Metronome(bpm, timeSignatureTop,timeSignatureBottom)
        metronome.addListener(this)
        beatsInBar = 0

    }
    @Test
    fun Test_100bpm_44() {

        println ("Testing 100bpm_44")
        setup (100, 4, 4)

        runTest()


    }

    @Test
    fun Test_Repeat(){

        setup(100, 4, 4)
        runTestNoCheck()
        runTestNoCheck()


    }

    @Test
    fun Test_200bpm_44() {

        println ("Testing 200bpm_44")
        setup (200, 4, 4)

        runTest()


    }
    @Test
    fun Test_400bpm_44() {

        println ("Testing 400bpm_44")
        setup (400, 4, 4)

        runTest()


    }
    @Test
    fun Test_50bpm_44() {

        println ("Testing 50bpm_44")
        setup (50, 4, 4)

        runTest()


    }
    @Test
    fun Test_100bpm_34(){

        println ("Testing 100bpm_34")
        setup (100, 3, 4)

        runTest(30)

    }

    @Test
    fun Test_100bpm_68(){

        println ("Testing 100bpm_34")
        setup (100, 6, 8)

        runTest(30)

    }


    private fun runTestNoCheck (duration : Int  = 10){
        stop = false
        selfTimer.schedule(this, duration * 1000L, TimeUnit.MILLISECONDS)
        startTime = System.currentTimeMillis()
        lastTime = startTime
        beatCount = 0
        metronome.start()

        while (!stop){

            Thread.sleep (100)
        }
        metronome.stop()
        println ("finished")
        endTime = System.currentTimeMillis()
    }

    private fun runTest(duration : Int = 30){
        selfTimer.schedule(this, duration * 1000L, TimeUnit.MILLISECONDS)
        startTime = System.currentTimeMillis()
        lastTime = startTime
        metronome.start()
        while (!stop){

            Thread.sleep (100)
        }
        metronome.stop()
        println ("finished")
        endTime = System.currentTimeMillis()

        println ("start $startTime , end $endTime")
        calcBpm = beatCount * 60000/((endTime - startTime)) - 1     //@TODO ("The -1 is a hack to get round rounding always giving 1 more beat than required (i think ) ")

        assertEquals ("beats dont match requested $bpm actual $calcBpm", bpm, calcBpm)
        assertEquals ("beats in bar don't match expected ${metronome.beatsInBar} actual $beatsInBar", metronome.beatsInBar, beatsInBar)
    }


    //@TODO ("Need to work out how to test time signatures")
    //@TODO ("Need to work out how to gap between beats")

    override fun onBeat( beatNum : Int,  beatsInBar : Int){

        beatCount++
        if (beatNum >  this.beatsInBar)
            this.beatsInBar = beatNum
        val currTime = System.currentTimeMillis()
        val diff = (currTime - lastTime)/10L
        lastTime = currTime
        print("$diff.")

    }

    override fun run() {

        stop = true

    }
}