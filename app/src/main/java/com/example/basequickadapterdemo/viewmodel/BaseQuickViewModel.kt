package com.example.basequickadapterdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class BaseQuickViewModel : ViewModel() {
    val datas: MutableLiveData<MutableList<String>> by lazy {
        MutableLiveData(
            mutableListOf(
                "heyangyang", "zhouaili", "hesanwei", "hexiangxiang", "hecuncun",
                "heyangyang", "zhouaili", "hesanwei", "hexiangxiang", "hecuncun",
                "heyangyang", "zhouaili", "hesanwei", "hexiangxiang", "hecuncun",
                "heyangyang", "zhouaili", "hesanwei", "hexiangxiang", "hecuncun"
            )
        )
    }

    suspend fun addData(data: String) {
        datas.apply {
            delay(2000)
            value?.add(0, data)
            postValue(value)
        }
    }
    fun removeData(position: Int) {
        datas.apply {
            value?.removeAt(position)
            updateDatas()
        }
    }

    private fun updateDatas(){
        datas.value = datas.value
    }

}