package com.example.basequickadapterdemo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainViewModel : ViewModel() {
    //Observer会接收到AsyncSubject onComplete 完成前的最后一个数据 如果不调用onComplete 那么就不会收到数据
    private val asyncSubject = AsyncSubject.create<String>()

    //Observer会接收到BehaviorSubject被订阅之前的最后一个数据，再接收其他发射过来的数据
    private val behaviorSubject = BehaviorSubject.create<String>()

    //PublishSubject 会接收到订阅之后发射的数据
    private val publishSubject = PublishSubject.create<String>()

    fun testPublishSubject() {
        Observable.create<String> {
                runBlocking {
                    delay(2000)
                    it.onNext("publish1")
                    it.onNext("publish2")
                }
            }
            .subscribeOn(Schedulers.io())
            .doOnNext {
                publishSubject.onNext(it)
            }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
    fun publishObservable(): Observable<String> = publishSubject.hide()

    fun testAsyncSubject() {
        Observable.create<Int> {
                runBlocking {
                    delay(2000)
                    it.onNext(1)
                }
            }
            .subscribeOn(Schedulers.io())
            .doOnNext {
                asyncSubject.onNext("done")
                asyncSubject.onNext("completeDone")
                runBlocking {
                    delay(2000)
                    asyncSubject.onComplete()
                }
            }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun testBehaviorSubject() {
        Observable.create<String> {
                runBlocking {
                    delay(2000)
                    it.onNext("behavior1")
                    it.onNext("behavior2")
                }
            }
            .subscribeOn(Schedulers.io())
            .doOnNext {
                behaviorSubject.onNext(it)
            }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }




    fun asyncObservable(): Observable<String> = asyncSubject.hide()

    fun behaviorObservable(): Observable<String> = behaviorSubject.hide()

    class MainViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel() as T
            }
            throw IllegalArgumentException("not a valid modelClass")
        }
    }
}