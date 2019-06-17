package com.example.nicsappp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*


fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

class MainActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView
    private lateinit var controller : MetronomeController

    public fun onClickPlus (view : android.view.View){

        var txtBPM: EditText = findViewById (R.id.textBpm)
        var bpm : Int = txtBPM.text.toString().toInt()

        if (bpm < 300) {
            bpm += 5
            txtBPM.text = bpm.toString().toEditable()
        }

    }

    public fun onClickMinus (view : android.view.View){

        var txtBPM: EditText = findViewById (R.id.textBpm)
        var bpm : Int = txtBPM.text.toString().toInt()

        if (bpm > 5) {
            bpm -= 5
            txtBPM.text = bpm.toString().toEditable()
        }
    }

    public fun onClickSetText ( view : android.view.View) {

        //var txtBox : EditText = findViewById(R.id.editText)
        //var lblTxt : TextView = findViewById(R.id.textLabel)

        //lblTxt.text = txtBox.text

        val btn : Button = findViewById(R.id.button)

       // val spinner : Spinner = findViewById (R.id.timeSignature)
        val txtBPM : EditText = findViewById (R.id.textBpm)
        val bpm : Int = txtBPM.text.toString().toInt()

        var rg : RadioGroup = findViewById(R.id.rgTimeSignature)
        var chk = rg.checkedRadioButtonId
        var rb : RadioButton = findViewById(chk)
        //val txtBPM = rb.text.toString()
        //val bpm : Int = txtBPM.toInt()


        //val timeSignature = spinner.selectedItem
        val timeSignature = rb.text.toString()
        var timeSignatureTop = 4
        var timeSignatureBottom = 4

        when (timeSignature){

            "2/4" -> {timeSignatureTop = 2
                        timeSignatureBottom = 4}
            "3/4" -> {timeSignatureTop = 3
                timeSignatureBottom = 4}
            "4/4" -> {timeSignatureTop = 4
                timeSignatureBottom = 4}
            "6/8" -> {timeSignatureTop = 8
                timeSignatureBottom = 8}
            else -> {timeSignatureTop = 4
                timeSignatureBottom = 4}

        }


        rg = findViewById(R.id.radioFirstBeat)
        chk = rg.checkedRadioButtonId

        var firstBeat : Int = chk

        when (firstBeat){

            R.id.firstBeatKick -> firstBeat = R.raw.quickkick
            R.id.firstBeatSnare -> firstBeat = R.raw.snare
            R.id.firstBeatHiHat -> firstBeat = R.raw.hihatclosed
            else  -> firstBeat = R.raw.quickkick


        }

        rg = findViewById(R.id.radioOtherBeat)
        chk = rg.checkedRadioButtonId

        var otherBeat : Int = chk

        when (otherBeat){

            R.id.otherBeatKick -> otherBeat = R.raw.quickkick
            R.id.otherBeatSnare -> otherBeat = R.raw.snare
            R.id.otherBeatHiHat -> otherBeat = R.raw.hihatclosed
            else  -> otherBeat = R.raw.quickkick
        }




        if (controller.isRunning()){
            controller.stop()

            btn.setText(R.string.set)
        }
        else {


            var metronome  = Metronome (bpm.toLong(), timeSignatureTop, timeSignatureBottom)
            controller.setMetronome(metronome)
            val clicker = MetronomeMultiSoundView(this, firstBeat, otherBeat)
            controller.setView (clicker)

            btn.setText(R.string.stop)
            controller.go()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val metronome = Metronome (100,4,4)
        val progressBar : ProgressBar = findViewById(R.id.progressBar2)
        val metronomeView = MetronomeProgressView(progressBar)
        val clicker = MetronomeMultiSoundView(this, R.raw.quickkick, R.raw.snare )
        var imageView : ImageView = findViewById(R.id.imageView)
        var drawView = MetronomeImageView(imageView, resources)
        controller = MetronomeController (metronomeView, clicker, metronome, drawView)




    }
}
