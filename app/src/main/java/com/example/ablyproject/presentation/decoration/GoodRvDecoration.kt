package com.example.ablyproject.presentation.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GoodRvDecoration (
    private val dividerWidth: Int
    ) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        // 홀수 아이템은 왼쪽에, 짝수 아이템은 오른쪽에 divider 를 설정한다.
        if (position % 2 == 0) outRect.right = dividerWidth else outRect.left = dividerWidth
    }
}
