package com.mygdx.game;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import com.badlogic.gdx.Game;
import com.mygdx.game.GameEngine;

public class ChancellorTest extends TesterFile 
{

    GameEngine testGameEngine;
    private Game testGame;

	@Before
	public void setup()
	{
        testGameEngine = new GameEngine(testGame,null);
	}

	@Test
	public void initialUpdate()
	{
		Chancellor chancellor = new Chancellor(testGameEngine);

		chancellor.update(0);

		assertTrue(chancellor.getCurrentTimeOnTile() == 0.0f);
		assertTrue(chancellor.getCurrentTile() != null);

	}

}
