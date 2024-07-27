import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat

class StickySearchViewBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    private var initialOffsetY = 0f

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        // Layout the child
        parent.onLayoutChild(child, layoutDirection)

        // Record the initial top offset of the child
        if (initialOffsetY == 0f) {
            initialOffsetY = child.top.toFloat()
        }

        // Position the child at its initial offset
        child.translationY = 0f
        return true
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed
        )

        // Calculate the new top position based on scroll
        val newTop = Math.min(initialOffsetY, target.scrollY.toFloat())
        child.translationY = -newTop
    }
}
