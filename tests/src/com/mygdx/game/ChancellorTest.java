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

	@Test
	public void hideChancellor()
	{
		Chancellor chancellor = new Chancellor(testGameEngine);

		chancellor.update(0);
		chancellor.update(0.6f);

		assertTrue(chancellor.getCurrentTile() == null);
	}

	public void changeTile()
	{
		Chancellor chancellor = new Chancellor(testGameEngine);

		chancellor.update(0);
		chancellor.update(0.6f);	

		chancellor.update(0.6f);


		assertTrue(chancellor.getCurrentTile() != null);
	}

	public void timeCalculation()
	{
		Chancellor chancellor = new Chancellor(testGameEngine);
		chancellor.update(0.7f);

		assertTrue(chancellor.getTime() == 16.0f-0.7f);
		assertTrue(chancellor.getCurrentTimeOnTile() == 0.7f);
	}

}
