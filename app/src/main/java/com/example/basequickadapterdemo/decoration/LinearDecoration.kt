package com.example.basequickadapterdemo.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.basequickadapterdemo.R

class LinearDecoration(private val context: Context) : RecyclerView.ItemDecoration() {

    val dividerHeight = context.resources.getDimension(R.dimen.divider_default_height).toInt()
    val dividerWidth = context.resources.getDimension(R.dimen.divider_default_width).toInt()

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
//        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        when (position) {
            0 -> outRect.set(dividerWidth, dividerHeight, dividerWidth, dividerHeight)
            else -> outRect.set(dividerWidth, 0, dividerWidth, dividerHeight)

        }
//        if (position == 0) {
//            outRect.set(dividerWidth, dividerHeight, dividerWidth, dividerHeight)
//        } else {
//            outRect.set(dividerWidth, 0, dividerWidth, dividerHeight)
//        }
    }

}