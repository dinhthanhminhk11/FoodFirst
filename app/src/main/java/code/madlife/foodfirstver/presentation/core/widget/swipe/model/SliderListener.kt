package code.madlife.foodfirstver.presentation.core.widget.swipe.model

import androidx.customview.widget.ViewDragHelper

interface SliderListener {
    /**
     * This is called when the [ViewDragHelper] calls it's
     * state change callback.
     *
     * @see ViewDragHelper.STATE_IDLE
     *
     * @see ViewDragHelper.STATE_DRAGGING
     *
     * @see ViewDragHelper.STATE_SETTLING
     *
     *
     * @param state     the [ViewDragHelper] state
     */
    fun onSlideStateChanged(state: Int)
    fun onSlideChange(percent: Float)
    fun onSlideOpened()

    /**
     * @return `true` than event was processed in the callback.
     */
    fun onSlideClosed(): Boolean
}