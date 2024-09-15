package code.madlife.foodfirstver.presentation.core.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.widget.swipe.model.SliderConfig
import code.madlife.foodfirstver.presentation.core.widget.swipe.model.SliderInterface
import code.madlife.foodfirstver.presentation.core.widget.swipe.model.SliderListener
import code.madlife.foodfirstver.presentation.core.widget.swipe.slider.SliderPanel
import timber.log.Timber

abstract class BaseSwipeableFragment<T : ViewBinding>(bindingInflater: (layoutInflater: LayoutInflater) -> T) : BaseTabFragment<T>(bindingInflater),
    SliderListener {

    private var rootFrameLayout: FrameLayout? = null
    private var contentView: View? = null

    protected var sliderInterface: SliderInterface? = null
        private set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contentView = super.onCreateView(inflater, container, savedInstanceState)
        rootFrameLayout = FrameLayout(requireContext()).also {
            it.setBackgroundColor(Color.TRANSPARENT)
            it.removeAllViews()
            it.addView(contentView)
        }
        return rootFrameLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSliderPanel()
    }

    private fun setUpSliderPanel() {
        contentView?.let { content ->
            if (sliderInterface == null) {
                val parent = content.parent as? ViewGroup ?: return
                val params = content.layoutParams
                parent.removeView(content)

                // Setup the slider panel and attach it
                val panel = SliderPanel(requireContext(), content, getSliderConfig())

                panel.addView(content)
                parent.addView(panel, 0, params)

                panel.setOnPanelSlideListener(FragmentPanelSlideListener(getSliderConfig()))

                sliderInterface = panel.defaultInterface
            }
        }
    }

    override fun onDestroyView() {
        rootFrameLayout?.removeAllViews()
        rootFrameLayout = null
        sliderInterface = null
        super.onDestroyView()
    }

    protected open fun getSliderConfig(): SliderConfig {
        return SliderConfig.Builder()
            .listener(this)
            .edgeSize(0.3f)
            .edge(true)
            .touchDisabledViews(getTouchDisabledViews())
            .build()
    }

    protected open fun getTouchDisabledViews(): List<View> {
        return emptyList()
    }

    override fun onSlideStateChanged(state: Int) {}

    override fun onSlideChange(percent: Float) {}

    override fun onSlideOpened() {}

    override fun onSlideClosed(): Boolean {
        return false
    }

    internal class FragmentPanelSlideListener(private val config: SliderConfig) : SliderPanel.OnPanelSlideListener {
        override fun onStateChanged(state: Int) {
            config.listener?.onSlideStateChanged(state)
        }

        override fun onClosed() {
            if (config.listener != null) {
                if (config.listener.onSlideClosed()) {
                    return
                }
            }

            Timber.e("onClosed: ")
            NavigationManager.getInstance().popBackStack()
        }

        override fun onOpened() {
            Timber.e("onOpened: ")
            config.listener?.onSlideOpened()
        }

        override fun onSlideChange(percent: Float) {
            Timber.e("onSlideChange: $percent")
            config.listener?.onSlideChange(percent)
        }
    }

    override fun getViewPager(): ViewPager2? {
        return null
    }
}