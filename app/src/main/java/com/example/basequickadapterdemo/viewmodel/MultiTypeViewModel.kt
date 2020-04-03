package com.example.basequickadapterdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MultiTypeViewModel : ViewModel() {

    val cutsCardNames: MutableLiveData<MutableList<String>> by lazy {
        MutableLiveData(
            mutableListOf(
                "Cut1", "Cut2", "Cut3", "Cut4", "Cut5"
            )
        )
    }

    val roundCardNames: MutableLiveData<MutableList<String>> by lazy {
        MutableLiveData(
            mutableListOf(
                "Round1", "Round2", "Round3", "Round4", "Round5"
            )
        )
    }

    val bookCardNames: MutableLiveData<MutableList<String>> by lazy {
        MutableLiveData(
            mutableListOf(
                "Book1", "Book2", "Book3", "Book4", "Book5"
            )
        )
    }

    init {
        initData()
    }

    private fun initData() {

    }

    override fun onCleared() {
        super.onCleared()
    }
}