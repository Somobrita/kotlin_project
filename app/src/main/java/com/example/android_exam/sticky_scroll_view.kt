package com.example.android_exam

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView
import android.view.ViewGroup.MarginLayoutParams

class StickyScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ScrollView(context, attrs, defStyle) {

    private var stickyView: View? = null
    private var stickyViewInitialTop: Int = 0

    fun setStickyView(view: View) {
        stickyView = view
        stickyView?.let {
            // Capture the initial top position of the sticky view
            stickyViewInitialTop = it.top
        }
    }

    override fun onScrollChanged(x: Int, y: Int, oldX: Int, oldY: Int) {
        super.onScrollChanged(x, y, oldX, oldY)
        stickyView?.let {
            val stickyViewHeight = it.height
            val viewHeight = height

            // Update sticky view position based on scroll
            if (y > stickyViewInitialTop) {
                // Set the sticky view to stick at the top
                it.translationY = (y - stickyViewInitialTop).toFloat()
                if (y + viewHeight > stickyViewInitialTop + stickyViewHeight) {
                    // Avoid pushing the sticky view below the bottom of the ScrollView
                    it.translationY = (stickyViewHeight - viewHeight).toFloat()
                }
            } else {
                // Reset the sticky view position when at the top
                it.translationY = 0f
            }
        }
    }
}
