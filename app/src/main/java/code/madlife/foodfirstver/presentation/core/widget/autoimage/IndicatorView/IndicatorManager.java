package code.madlife.foodfirstver.presentation.core.widget.autoimage.IndicatorView;

import androidx.annotation.Nullable;

import code.madlife.foodfirstver.presentation.core.widget.autoimage.IndicatorView.animation.AnimationManager;
import code.madlife.foodfirstver.presentation.core.widget.autoimage.IndicatorView.animation.controller.ValueController;
import code.madlife.foodfirstver.presentation.core.widget.autoimage.IndicatorView.animation.data.Value;
import code.madlife.foodfirstver.presentation.core.widget.autoimage.IndicatorView.draw.DrawManager;
import code.madlife.foodfirstver.presentation.core.widget.autoimage.IndicatorView.draw.data.Indicator;

public class IndicatorManager implements ValueController.UpdateListener {

    private DrawManager drawManager;
    private AnimationManager animationManager;
    private Listener listener;

    interface Listener {
        void onIndicatorUpdated();
    }

    IndicatorManager(@Nullable Listener listener) {
        this.listener = listener;
        this.drawManager = new DrawManager();
        this.animationManager = new AnimationManager(drawManager.indicator(), this);
    }

    public AnimationManager animate() {
        return animationManager;
    }

    public Indicator indicator() {
        return drawManager.indicator();
    }

    public DrawManager drawer() {
        return drawManager;
    }

    @Override
    public void onValueUpdated(@Nullable Value value) {
        drawManager.updateValue(value);
        if (listener != null) {
            listener.onIndicatorUpdated();
        }
    }
}

