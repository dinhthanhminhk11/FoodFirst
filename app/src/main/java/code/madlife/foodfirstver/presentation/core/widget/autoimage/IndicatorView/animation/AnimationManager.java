package code.madlife.foodfirstver.presentation.core.widget.autoimage.IndicatorView.animation;

import androidx.annotation.NonNull;

import code.madlife.foodfirstver.presentation.core.widget.autoimage.IndicatorView.animation.controller.AnimationController;
import code.madlife.foodfirstver.presentation.core.widget.autoimage.IndicatorView.animation.controller.ValueController;
import code.madlife.foodfirstver.presentation.core.widget.autoimage.IndicatorView.draw.data.Indicator;

public class AnimationManager {

    private AnimationController animationController;

    public AnimationManager(@NonNull Indicator indicator, @NonNull ValueController.UpdateListener listener) {
        this.animationController = new AnimationController(indicator, listener);
    }

    public void basic() {
        if (animationController != null) {
            animationController.end();
            animationController.basic();
        }
    }

    public void interactive(float progress) {
        if (animationController != null) {
            animationController.interactive(progress);
        }
    }

    public void end() {
        if (animationController != null) {
            animationController.end();
        }
    }
}
