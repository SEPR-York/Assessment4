package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.mygdx.game.Main;
import com.mygdx.game.Player;
import com.mygdx.game.Roboticon;
import com.mygdx.game.Tile;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Gandhi-Inc.
 * @version Assessment 4
 *          An executable version of the game can be found at: http://gandhi-inc.me/downloads/assessment4.jar
 *          Our website is: www.gandhi-inc.me
 */

public class RoboticonTest extends TesterFile {

    private Game game = new Main();
    private GameEngine engine = new GameEngine(game, null);
    private Player TestPlayer = new Player(0, "player1");
    private Tile TestTile = new Tile(game, engine, 0,0, 0, 0, true, new Runnable() {
        @Override
        public void run() {

        }
    });
    private Roboticon TestRobot = new Roboticon(0, TestPlayer, TestTile);

    /**
     * Tests that the Roboticon.upgrade(String resource) method works as intended for Valid and invalid values.
     */
    @Test
    public void testupgrade() {
        int NewLevel[] = {2, 1, 1};
        TestRobot.upgrade(0);
        assertTrue(NewLevel[0] == TestRobot.getLevel()[0]);
        NewLevel[1] = 2;
        TestRobot.upgrade(1);
        assertTrue(NewLevel[1] == TestRobot.getLevel()[1]);
        NewLevel[2] = 2;
        TestRobot.upgrade(2);
        assertTrue(NewLevel[2] == TestRobot.getLevel()[2]);

    }

    /**
     * Test confirming the possibleUpgrades method returns the possible upgrades available, as an array.
     */
    @Test
    public void testpossibleUpgrades() {
        int TestUpgrades[] = TestRobot.getLevel();
        for (int i = 0; i < 3; i++){
            TestUpgrades[i] += 1;
        }
        assertArrayEquals(TestRobot.possibleUpgrades(), TestUpgrades);
    }

    /**
     * Test confirming productionModifier method works and provides values within a reasonable range
     */
    @Test
    public void testproductionModifier() {
        int[] Modifiers;
        for (int j = 0; j < 100; j++) {
            Modifiers = TestRobot.productionModifier();
            for (int i = 0; i < 3; i++) {
                assertTrue(Modifiers[i] < 6);
            }
        }
    }

}

