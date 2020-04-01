package com.example.basequickadapterdemo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.basequickadapterdemo.databinding.FragmentMainBinding
import com.example.basequickadapterdemo.viewmodel.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    lateinit var mBinding: FragmentMainBinding

    val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModel.MainViewModelFactory()).get(MainViewModel::class.java)
    }

    //mvp 架构中 一般是presenter 持有view 和 dataProvider的引用
    //presenter通过dataProvider获取数据后 再拿到view的引用 调用view的方法 设置数据
    //presenter 也可以在activity 对应的生命周期处理相关对象的引用和释放 以防止内存泄漏

    //mvvm 架构呢 一般是viewModel 来做view 和 data层的桥梁 需要注意的是 viewModel中一般是没有持有view的引用的
    //viewModel中 通过dataProvider 拿到数据后  由于view层已经注册了数据监听(观察者模式)
    //这个时候viewModel 将数据分发下去 ， 触发了view层的数据监听 就会更新界面
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMainBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
      //  (requireActivity() as AppCompatActivity).setSupportActionBar(mBinding.toolbar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ensureListeners()
        ensureSubscribe()
    }

    private fun ensureSubscribe() {
        mainViewModel.asyncObservable()
            .doOnNext {
                Log.d(TAG, "asyncSubject onNext ---> $it")
            }
            .doOnComplete {
                Log.d(TAG, "asyncSubject onComplete")
            }
            .doOnError {
                Log.d(TAG, "asyncSubject onError --> ${it.message}")
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        mainViewModel.behaviorObservable()
            .doOnNext {
                Log.d(TAG, "behaviorSubject onNext ---> $it")
            }
            .doOnComplete {
                Log.d(TAG, "behaviorSubject onComplete")
            }
            .doOnError {
                Log.d(TAG, "behaviorSubject onError --> ${it.message}")
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        mainViewModel.publishObservable()
            .doOnNext {
                Log.d(TAG, "publishSubject onNext ---> $it")
            }
            .doOnComplete {
                Log.d(TAG, "publishSubject onComplete")
            }
            .doOnError {
                Log.d(TAG, "publishSubject onError --> ${it.message}")
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }

    private fun ensureListeners() {
        mBinding.btnToBaseQuickAdapter.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_baseQuickAdapterFragment)
        }

        mBinding.btnAsyncSubject.setOnClickListener {
            mainViewModel.testAsyncSubject()
        }

        mBinding.btnBehaviorSubject.setOnClickListener {
            mainViewModel.testBehaviorSubject()
        }

        mBinding.btnPublicSubject.setOnClickListener {
            mainViewModel.testPublishSubject()
        }
    }

    companion object {
        val TAG = "MainFragment"
    }
}
