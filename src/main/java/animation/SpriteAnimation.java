package animation;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {
    private final ImageView imageView;
    private final int count;
    private int offsetX;
    private final int width;
    private final int height;

    public SpriteAnimation(
            ImageView imageView,
            Duration duration,
            int count, int offsetX,
            int width, int height
    ){
        this.imageView = imageView;
        this.count = count;
        this.offsetX = offsetX;
        this.width = width;
        this.height = height;
        setCycleDuration(duration);
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
        this.imageView.setViewport(new Rectangle2D(offsetX, 0, width, height));
    }

    public void setOffsetX(int x){
        this.offsetX = x;
    }

    @Override
    protected void interpolate(double frac) {
        int x;
        final int index = Math.min((int)Math.floor(count*frac), count-3);
        if (offsetX == 908) x = 908;
        else x = (index + 1) * width + offsetX;
        imageView.setViewport(new Rectangle2D(x, 0, width, height));
    }
}
