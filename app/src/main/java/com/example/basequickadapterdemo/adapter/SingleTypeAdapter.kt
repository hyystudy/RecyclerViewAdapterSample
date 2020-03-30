package com.example.basequickadapterdemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.basequickadapterdemo.R
import com.example.basequickadapterdemo.databinding.ItemSingleTypeBinding

class SingleTypeAdapter :
    BaseQuickAdapter<String, BaseDataBindingHolder<ItemSingleTypeBinding>>(R.layout.item_single_type) {

    override fun convert(holder: BaseDataBindingHolder<ItemSingleTypeBinding>, item: String) {
        holder.dataBinding?.apply {
            data = item
            executePendingBindings()
        }
    }

}