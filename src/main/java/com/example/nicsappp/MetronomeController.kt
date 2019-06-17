package com.example.nicsappp

class MetronomeController constructor (val view : MetronomeProgressView, var click : MetronomeMultiSoundView, _metronome : Metronome, val drawView : MetronomeImageView) : MetronomeListener {

    private var metronome : Metronome

    init {
        metronome = _metronome
        metronome.addListener(this)
    }

    fun go (){

        view.setMax(metronome.beatsInBar)

        metronome.start()
    }

    fun stop(){

        metronome.stop()

    }

    override fun onBeat( beatNum : Int,  beatsInBar : Int){

        val firstBeat : Boolean

        when (beatNum){
            1 -> firstBeat = true
            else -> firstBeat = false
        }
        click.play(firstBeat)
        drawView.beat(beatNum, beatsInBar)
       // view.setProgress(beatNum)

    }

    fun isRunning () : Boolean {

        return metronome.isRunning()
    }


    fun setMetronome ( metronome : Metronome){

        this.metronome = metronome
        this.metronome.addListener(this)
    }

    fun setView  (view : MetronomeMultiSoundView){

        click = view
    }

}