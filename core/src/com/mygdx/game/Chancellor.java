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

	GameEngine gameEngine;									// Create a gameEngine object

	/**
	 * The method that creates the object chancellor
	 * @param engine uses the game engine
	 */
	public Chancellor(GameEngine engine)					
	{
		gameEngine = engine;								// The engine is set to the gameEngine
	}

	public static final float INTERVAL = 1.0f;				// Define the interval that the chancellor will appear on a new tile
	float time = 16.0f;										// Define the time the process will take
	
	float currentTimeOnTile = 0.0f;							// Defines the current time on tile to 0

	Tile currentTile = null;								// Sets the current tile to empty

	/**
	 * Method to choose a random tile from the 16 available
	 */
	private void changeTile()
	{
		Random rand1 = new Random();
		int randomTile = rand1.nextInt(16);					// Choose a random tile (between 1-16)
		Tile[] listOfTiles = gameEngine.getTiles();			// Define the tiles as "listOfTiles"
		currentTile = listOfTiles[randomTile];				// Define "tile" as a random tile amongst the list
		currentTimeOnTile = 0;								// Set the current time on the tile to 0
		currentTile.showchancellorTexture();				// Set the chancellor texture to visible in tile class
	}

	/**
	 * Removes the chancellor from a tile
	 */
	private void removeFromTile()
	{
		if (currentTile != null)							// If the current tile is not empty
		{	
			currentTile.hidechancellorTexture();			// Hide the chancellor texture in the tile class
			currentTile = null;								// Set the current tile to empty
		}
	}

	/**
	 * Updates the tile image using delta time
	 * @param dTime - calls the delta time used throughout the game engine
	 */
	public void update(float dTime)
	{	
		if (time > 0)										// If the time is bigger than 0
		{	
			if (time == 16.0f)								// If the time has reached 16
			{
				changeTile();								// Change tile
			}
			currentTimeOnTile += dTime;						// Add delta to the current time
			time -= dTime;									// Take away delta from time 

			if ((currentTimeOnTile > (INTERVAL / 2)) && (currentTile != null))	// Allow the chancellor to be visible half the time of the interval
			{
				removeFromTile();							// Call the remove from tile method
			}
			else if (currentTimeOnTile > INTERVAL)			// When the current time is bigger tan the interval then 
			{
				changeTile();
			}
		}
	}

	/**
	 * Method to reset the game and make the chancellor hidden on all tiles
	 */
	public void reset()
	{
		for (Tile tile : gameEngine.getTiles())				// For all the tiles
		{
				tile.hidechancellorTexture();				// Hide the chancellor
		}
	}

	/**
	 * Getter for time
	 * Used for testing the class
	 * @return the time
	 */
	public float getTime()
	{
		return time;										// Returns the time
	} 

	/**
	 * Getter for the current time on tile
	 * Used for testing class
	 * @return the current time spent on the tile
	 */
	public float getCurrentTimeOnTile()
	{
		return currentTimeOnTile;							// Returns the current time spent on the tile
	}

	/**
	 * Getter for the current tile
	 * Used for testing class
	 * @return the current tile the chancellor is on
	 */
	public Tile getCurrentTile()
	{
		return currentTile;									// Returns the current tile
	}

}

