package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Gandhi-Inc.
 * @version Assessment 4
 *          An executable version of the game can be found at: http://gandhi-inc.me/downloads/assessment4.jar
 *          Our website is: www.gandhi-inc.me
 */

public class ResourceAccessTest extends TesterFile{

    @Test
    public void TestsHaveAssets(){
        assertTrue(Gdx.files.internal("image/Roboticon111.png").exists());
    }
}
