package io.github.teamfractal.animation;

import com.badlogic.gdx.graphics.g2d.Batch;
import io.github.teamfractal.screens.AbstractAnimationScreen;

public interface IAnimation {
    /**
     * Draw animation on screen.
     *
     * @param delta     Time change since last call.
     * @param screen    The screen to draw on.
     * @param batch     The Batch for drawing stuff.
     * @return          return <code>true</code> if the animation has completed.
     */
    boolean tick(float delta, AbstractAnimationScreen screen, Batch batch);

    /**
     * Set the animation finish callback.
     * @param callback   The callback function.
     */
    void setAnimationFinish(IAnimationFinish callback);
    void callAnimationFinish();
    void cancelAnimation();
	AnimationType getType();

    public enum AnimationType {
    	Tile,
	    Overlay
    }
}
