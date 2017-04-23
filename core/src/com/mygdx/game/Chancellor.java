package com.mygdx.game;

import java.util.Random;

public class Chancellor 
{
	
	/**
	 * isHit looks to see if the tile is clicked while the chancellor is on the tile
	 * @return It will return a true or false value. True if tile is hit
	 */
	public boolean isHit(Tile tile)
	{
		// Checks to see if the tile is clicked on
		return false;
	}
	
	/**
	 * The Chancellor will appear on the tile selected for 500ms
	 * @param tile
	 * @throws InterruptedException 
	 */
	public void appears(Tile tile) throws InterruptedException
	{
		tile.showchancellorTexture();								// Add the chancellor to the tile
		long currentTime = System.currentTimeMillis();		// Calculate the current game time
		long endtime = System.currentTimeMillis() + 500;	// Calculate the game time + 500ms
		while (endtime - currentTime > 0)					// While the game counts 500ms 
		{
			if(isHit(tile) == true)								// Checks if the tile has been hit
			{
				//Add score to player
				//Exit the game
			}
			currentTime = System.currentTimeMillis();		// Updates the current game time
		}
		tile.hidechancellorTexture();							// Remove the chancellor from the tile
		
		//Make the game sleep for 500ms 
		try {
		    Thread.sleep(500);								// Sleep for 500ms
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();				// Catch the interrupted thread
		}	
		
	}
	
	public void randomlyChooseTile(Player player)
	{
		Random rand0 = new Random(); 						// Random to determine if chancellor will appear this round
		Random rand1 = new Random();						// Random to determine which tile
		
		int random = rand0.nextInt(2);
		int randomTile = rand1.nextInt(player.getTileList().size());
		
		Tile tile = player.getTileList().get(randomTile);
		
		if (random == 1)
		{
			try 
			{
				System.out.println("Chancellor appears");
				appears(tile);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
	}
}

