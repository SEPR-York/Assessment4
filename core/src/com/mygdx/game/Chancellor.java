package com.mygdx.game;

import java.util.Random;

/**
 * This class is the Chancellor mini game. It will make the chancellor appear on a random a random tile 
 * 
 * @author Gandhi-Inc.
 * @version Assessment 4
 *      An executable version of the game can be found at: http://gandhi-inc.me/downloads/assessment4.jar
 *      Our website is: www.gandhi-inc.me
 *
 */
public class Chancellor 
{	

	GameEngine gameEngine;

	public Chancellor(GameEngine engine)
	{
		gameEngine = engine;
	}

	public static final float INTERVAL = 1.0f;
	float time = 16.0f;
	
	float currentTimeOnTile = 0.0f;

	Tile currentTile = null;

	private void changeTile()
	{
		Random rand1 = new Random();
		int randomTile = rand1.nextInt(16);		// Choose a random tile (between 1-16)
		Tile[] listOfTiles = gameEngine.getTiles();	// Define the tiles as "listOfTiles"
		currentTile = listOfTiles[randomTile];	// Define "tile" as a random tile amongst the list
		currentTimeOnTile = 0;
		currentTile.showchancellorTexture();
	}

	private void removeFromTile()
	{
		if (currentTile != null)
		{	
			currentTile.hidechancellorTexture();
			currentTile = null;
		}
	}

	public void update(float dTime)
	{
		if (time > 0)
		{	
			if (time == 16.0f)
				changeTile();

			currentTimeOnTile += dTime;
			time -= dTime;

			if ((currentTimeOnTile > (INTERVAL / 2)) && (currentTile != null))
			{
				removeFromTile();
			}
			else if (currentTimeOnTile > INTERVAL)
			{
				changeTile();
			}
		}
	}

	public void reset()
	{
		for (Tile tile : gameEngine.getTiles())
				tile.hidechancellorTexture();
	}

	public float getTime()
	{
		return time;
	} 

	public float getCurrentTimeOnTile()
	{
		return currentTimeOnTile;
	}

	public Tile getCurrentTile()
	{
		return currentTile;
	}

}

