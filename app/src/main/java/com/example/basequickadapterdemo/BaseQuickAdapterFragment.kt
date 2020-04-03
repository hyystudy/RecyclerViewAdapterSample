package com.example.basequickadapterdemo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basequickadapterdemo.adapter.SingleTypeAdapter
import com.example.basequickadapterdemo.databinding.FragmentBaseQuickAdapterBinding
import com.example.basequickadapterdemo.decoration.LinearDecoration
import com.example.basequickadapterdemo.viewmodel.BaseQuickViewModel
import kotlinx.coroutines.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BaseQuickAdapterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BaseQuickAdapterFragment : Fragment() {

    lateinit var mBinding: FragmentBaseQuickAdapterBinding
    private val mSingleTypeAdapter by lazy { SingleTypeAdapter() }

    private val mViewModel: BaseQuickViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(BaseQuickViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentBaseQuickAdapterBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ensureViews()
        ensureListeners()
        ensureData()
    }

    private fun ensureListeners() {
        mSingleTypeAdapter.setOnItemClickListener { adapter, view, position ->
            Toast.makeText(requireContext(), mSingleTypeAdapter.data[position], Toast.LENGTH_SHORT).show()
        }
        //toolbar
        mBinding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        mBinding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_down -> {
                    mBinding.rvList.smoothScrollToPosition(mSingleTypeAdapter.itemCount-1)
                }
                R.id.action_up -> {
                    mBinding.rvList.smoothScrollToPosition(0)
                }
                R.id.action_add -> {
                    GlobalScope.launch(Dispatchers.Main) {
                        mViewModel.addData("Add")
                    }
                }
                R.id.action_remove -> {
                    mViewModel.removeData(1)
                }
            }
            return@setOnMenuItemClickListener true
        }

        mBinding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }
        })

    }

    private fun ensureData() {

        mViewModel.datas.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "data come in")
            mSingleTypeAdapter.setList(it)
        })

    }

    private fun ensureViews() {
        mBinding.rvList.layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvList.addItemDecoration(LinearDecoration(requireContext()))

        mBinding.rvList.adapter = mSingleTypeAdapter

        //
        //mBinding.rvList.smoothScrollToPosition(singleTypeAdapter.itemCount -1)
    }

    companion object {
        val TAG = "BaseAdapterFragment"
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BaseQuickAdapterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
