package com.mygdx.game;

import java.util.Random;

/**
 * @author Gandhi-Inc.
 * @version Assessment 4
 *          An executable version of the game can be found at: http://gandhi-inc.me/downloads/assessment4.jar
 *          Our website is: www.gandhi-inc.me
 */

public abstract class RandomEvent {

    // Fields shared by all random events
    Random randomiser;
    private GameScreen gameScreen;
    private int eventCooldown;
    protected int duration;


    RandomEvent(GameScreen gameScreen) {
        this.randomiser = new Random();
        this.gameScreen = gameScreen;
        this.eventCooldown = 0;
        this.duration = 2;
    }

    /**
     * Abstract method which implements the back-end effects of a random event.
     * Required to be implemented by all sub-classes
     * @param doOrUndo: boolean determining whether to cause the event effect, or
     *                reverse it. True = Cause, False = Reverse.
     */
    public abstract void eventEffect(boolean doOrUndo);

    /**
     * Abstract method which generates a string to display in the random event message
     * overlay during gameplay.
     * Required to be implemented by all sub-classes.
     * @param doOrUndo: boolean determining whether to cause the event effect, or
     *                reverse it. True = Cause, False = Reverse.
     * @return: A String containing the message to be displayed.
     */
    public abstract String eventMessage(boolean doOrUndo);

    /**
     * Method which returns the value that the duration of an event must decrement to in
     * order for it to be removed from the game. When this value is reached, a new event of
     * the same type is allowed to occur.
     * Method is inherited by sub-classes.
     * @return: An integer -x, where x is the number of turns the effect needs to cool down for.
     */
    protected int getEventCooldown() {
        return this.eventCooldown;
    }

    /**
     * Method which calls eventEffect() and eventMessage() in sequence, and displays the message
     * in the GUI. This method should be used to trigger a random event, rather than calling
     * eventEffect() or eventMessage() directly.
     * @param doOrUndo: boolean determining whether to cause the event effect, or
     *                reverse it. True = Cause, False = Reverse.
     */
    public void eventHappen(boolean doOrUndo) {
        this.eventEffect(doOrUndo);
        String message = this.eventMessage(doOrUndo);
        gameScreen.showEventMessage(message);

        if (!doOrUndo) {this.eventCooldown = -2;}

        System.out.println(message);
    }

    /**
     * Method which gets the duration remaining of the current random event.
     * @return: An int value denoting the number of turns the random event has left.
     */
    protected int getDuration() {return this.duration;}

    /**
     * Method which decrements the duration of the event by 1.
     */
    protected void decDuration() {this.duration -= 1;}
}
