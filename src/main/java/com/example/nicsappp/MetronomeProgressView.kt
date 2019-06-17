package com.example.nicsappp

import android.widget.ProgressBar

class MetronomeProgressView (val progressBar: ProgressBar){


    fun setMax(max : Int){
        progressBar.max = max
    }

    fun setProgress(progress: Int){
        progressBar.progress = progress
    }


}