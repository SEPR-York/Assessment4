import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.GameTimer;
import com.mygdx.game.TTFont;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Gandhi-Inc.
 * @version Assessment 4
 *          An executable version of the game can be found at: http://gandhi-inc.me/downloads/assessment4.jar
 *          Our website is: www.gandhi-inc.me
 */


public class GameTimerTest extends TesterFile {
    private boolean complete = false;
    private GameTimer TestTimer = new GameTimer(3, new TTFont(Gdx.files.internal("font/testfontbignoodle.ttf"), 120), Color.WHITE, new Runnable() {
        @Override
        public void run() {
            TimerComplete();
        }
    });


    private void TimerComplete(){
        this.complete = true;
    }

    /**
     * Tests that the GameTimer class executes it's runnable on complettion
     * @throws Exception incase sleep is interrupted
     */
    @Test
    public void testTimerCompletion() throws Exception{
        assertFalse(this.complete);
        TestTimer.start();
        Thread.sleep(3100);
        assertTrue(this.complete);
    }
}

