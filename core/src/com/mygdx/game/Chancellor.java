package com.mygdx.game;

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
	public void Appears(Tile tile) throws InterruptedException
	{
		tile.addChancellor();								// Add the chancellor to the tile
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
		tile.removeChancellor();							// Remove the chancellor from the tile
		
		//Make the game sleep for 500ms 
		try {
		    Thread.sleep(500);								// Sleep for 500ms
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();				// Catch the interrupted thread
		}	}
	
	
	
}

