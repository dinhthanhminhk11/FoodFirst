package code.madlife.foodfirstver.presentation.core.widget.autoimage.IndicatorView.animation.data.type;
import code.madlife.foodfirstver.presentation.core.widget.autoimage.IndicatorView.animation.data.Value;
public class ScaleAnimationValue extends ColorAnimationValue implements Value {

    private int radius;
    private int radiusReverse;

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRadiusReverse() {
        return radiusReverse;
    }

    public void setRadiusReverse(int radiusReverse) {
        this.radiusReverse = radiusReverse;
    }
}
