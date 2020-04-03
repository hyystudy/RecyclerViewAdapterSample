package com.example.basequickadapterdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.basequickadapterdemo.adapter.ITEM_TYPE_BOOK_CARD
import com.example.basequickadapterdemo.adapter.ITEM_TYPE_CUT_CARD
import com.example.basequickadapterdemo.adapter.ITEM_TYPE_ROUND_CARD
import com.example.basequickadapterdemo.adapter.MultiTypeAdapter
import com.example.basequickadapterdemo.databinding.FragmentMultiTypeAdapterBinding
import com.example.basequickadapterdemo.decoration.LinearDecoration
import com.example.basequickadapterdemo.viewmodel.MultiTypeViewModel
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MultiTypeAdapterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MultiTypeAdapterFragment : Fragment() {

    private val mViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MultiTypeViewModel::class.java)
    }

    private val mAdapter by lazy { MultiTypeAdapter() }

    lateinit var mBinding: FragmentMultiTypeAdapterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMultiTypeAdapterBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListeners()
        initSubscribe()
    }

    private fun initListeners() {
        mBinding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            when(adapter.getItemViewType(position)) {
                ITEM_TYPE_CUT_CARD -> {
                    Snackbar.make(view, "Cut Card $position", Snackbar.LENGTH_SHORT).show()
                }
                ITEM_TYPE_ROUND_CARD -> {
                    Snackbar.make(view, "Round Card $position", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        mAdapter.addChildClickViewIds(R.id.book_card)
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(adapter.getItemViewType(position)) {
                ITEM_TYPE_BOOK_CARD -> {
                    Snackbar.make(view, "Book Card $position", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initSubscribe() {
        mViewModel.cutsCardNames.observe(viewLifecycleOwner, Observer {
            it.map { name->
                MultiTypeAdapter.CutCardItem(name)
            }.apply {
                mAdapter.data.addAll(0, this)
            }
        })
        mViewModel.roundCardNames.observe(viewLifecycleOwner, Observer {
            it.map { name->
                MultiTypeAdapter.RoundCardItem(name = name)
            }.apply {
                mAdapter.data.addAll(this)
            }
        })
        mViewModel.bookCardNames.observe(viewLifecycleOwner, Observer {
            it.map { name->
                MultiTypeAdapter.BookCardItem(name = name)
            }.apply {
                mAdapter.data.addAll(this)
            }
        })
    }

    private fun initView() {

        mBinding.rvList.layoutManager = GridLayoutManager(requireContext(), 3)
        mBinding.rvList.addItemDecoration(LinearDecoration(requireContext()))
        mBinding.rvList.adapter = mAdapter

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MultiTypeAdapterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
