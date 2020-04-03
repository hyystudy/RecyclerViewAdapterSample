package com.example.basequickadapterdemo.adapter

import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.listener.GridSpanSizeLookup
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.basequickadapterdemo.R


const val ITEM_TYPE_CUT_CARD = 0
const val ITEM_TYPE_ROUND_CARD = 1
const val ITEM_TYPE_BOOK_CARD = 3

class MultiTypeAdapter constructor(
    private val list: MutableList<MultiItemEntity> = mutableListOf()
) : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data = list) {

    init {
        addItemType(ITEM_TYPE_CUT_CARD, R.layout.item_cut_card)
        addItemType(ITEM_TYPE_ROUND_CARD, R.layout.item_round_card)
        addItemType(ITEM_TYPE_BOOK_CARD, R.layout.item_book_card)

        setGridSpanSizeLookup { _, viewType, _ -> if (viewType == ITEM_TYPE_BOOK_CARD) 1 else 3}
    }

    override fun convert(holder: BaseViewHolder, item: MultiItemEntity) {
        when(holder.itemViewType) {
            ITEM_TYPE_CUT_CARD -> {
                val cutCardItem = item as CutCardItem
                holder.setText(R.id.name, cutCardItem.name)
            }
            ITEM_TYPE_ROUND_CARD -> {
                val roundCardItem = item as RoundCardItem
                holder.setText(R.id.name, roundCardItem.name)
            }
            ITEM_TYPE_BOOK_CARD -> {
                val bookCardItem = item as BookCardItem
                holder.setText(R.id.name, bookCardItem.name)
            }
        }
    }

    class CutCardItem(val name: String) : MultiItemEntity {
        override val itemType: Int
            get() = ITEM_TYPE_CUT_CARD

    }

    class RoundCardItem(override val itemType: Int = ITEM_TYPE_ROUND_CARD, val name: String) : MultiItemEntity

    class BookCardItem(override val itemType: Int = ITEM_TYPE_BOOK_CARD, val name: String, val url: String = "") : MultiItemEntity


}