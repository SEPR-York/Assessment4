package com.mygdx.game;

import java.util.Random;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

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

	float time = 30.0f;
	float interval = 1.0f;
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
			if (time == 30.0f)
				changeTile();

			currentTimeOnTile += dTime;
			time -= dTime;

			if ((currentTimeOnTile > (interval / 2)) && (currentTile != null))
			{
				removeFromTile();
			}
			else if (currentTimeOnTile > interval)
			{
				changeTile();
			}
		}
	}

	public void reset()
	{
		removeFromTile();
	}

/*
	
	 * isHit looks to see if the tile is clicked while the chancellor is on the tile
	 * @return It will return a true or false value. True if tile is hit
	 *
	public boolean isHit(Tile tile)
	{
		// Checks to see if the tile is clicked on
		return false;
	}
	
	/**
	 * The Chancellor will appear on the tile selected for 500ms
	 * @param tile
	 * @throws InterruptedException 
	 *
	public void appears(Tile tile, Player player, GameEngine engine) throws InterruptedException
	{
		System.out.println("appears (chancellor class)");
		tile.showchancellorTexture();						// Add the chancellor to the tile
		
		engine.getGameScreen().render(1);	// SHOULD RENDER THE SCREEN AGAIN?!
		
		long currentTime = System.currentTimeMillis();		// Calculate the current game time
		long endtime = System.currentTimeMillis() + 500;	// Calculate the game time + 500ms
		while (endtime - currentTime > 0)					// While the game counts 500ms 
		{
			if(isHit(tile) == true)							// Checks if the tile has been hit
			{
				player.setMoney(player.getMoney() + 20); 	// Finds the player's money and adds 20
				// Exit the game
			}
			currentTime = System.currentTimeMillis();		// Updates the current game time
		}
		tile.hidechancellorTexture();						// Remove the chancellor from the tile
		engine.getGameScreen().render(1); // SHOULD RENDER THE SCREEN AGAIN?!
		
		//Make the game sleep for 500ms 
		try {
		    Thread.sleep(500);								// Sleep for 500ms
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();				// Catch the interrupted thread
		}
					
	}
	
	public void randomlyChooseTile(Player player, GameEngine engine)
	{
		Random rand0 = new Random(); 						// Random to determine if chancellor will appear this round
		Random rand1 = new Random();						// Random to determine which tile
		
		int random = rand0.nextInt(2);						// Set the int called random to either 1 or 2 (50% chance)
		
		
		if (random == 1)									// If random was 1
		{
			try 											// Try - in case it throws errors
			{
				// Opens up a JOptionPane to inform the player that the mini game is about to take place
				JOptionPane chancellorOptionPane = new JOptionPane("You need to catch the Chancellor! If you manage to catch him there is a prize!");
	        	JDialog chancellorDialog = chancellorOptionPane.createDialog("Catch the Chancellor!");
	        	chancellorDialog.setAlwaysOnTop(true);		// Make sure that it appears on top and is visible
	        	chancellorDialog.setVisible(true);			// Set the dialog box to visible
	        	
				for(int i = 0; i < 30; i++)					// Make the chancellor appear 30 times
				{
					int randomTile = rand1.nextInt(16);		// Choose a random tile (between 1-16)
					Tile[] listOfTiles = engine.getTiles();	// Define the tiles as "listOfTiles"
					Tile tile = listOfTiles[randomTile];	// Define "tile" as a random tile amongst the list
					System.out.println("Chancellor appears on tile " + tile.getID());	// Print which tile the chancellor appears on
					appears(tile, player, engine); 			// Call the function appears
				}
				
			} 
			catch (InterruptedException e) 					// Catch the error if any thrown
			{
				e.printStackTrace();						// Trace the error back up the stack
			}
		}
		
	}*/
}

