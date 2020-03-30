package com.example.basequickadapterdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.example.basequickadapterdemo.adapter.SingleTypeAdapter
import com.example.basequickadapterdemo.databinding.FragmentBaseQuickAdapterBinding
import com.example.basequickadapterdemo.decoration.LinearDecoration

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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var mBinding: FragmentBaseQuickAdapterBinding
    private val singleTypeAdapter by lazy { SingleTypeAdapter() }

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
        initListeners()
        initData()
    }

    private fun initListeners() {
        singleTypeAdapter.setOnItemClickListener { adapter, view, position ->
            Toast.makeText(requireContext(), singleTypeAdapter.data[position], Toast.LENGTH_SHORT).show()
        }
        mBinding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun initData() {
        val data = mutableListOf(
            "heyangyang", "zhouaili", "hesanwei", "hexiangxiang", "hecuncun"
        )
        singleTypeAdapter.setNewInstance(data)
    }

    private fun ensureViews() {
        mBinding.rvList.layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvList.addItemDecoration(LinearDecoration(requireContext()))

        mBinding.rvList.adapter = singleTypeAdapter
    }

    companion object {
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
