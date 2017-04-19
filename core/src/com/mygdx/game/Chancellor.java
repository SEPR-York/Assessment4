package com.mygdx.game;

public class Chancellor 
{
	
	/**
	 * isHit looks to see if the tile is clicked while the chancellor is on the tile
	 * @return It will return a true or false value. True if tile is hit
	 */
	public boolean isHit()
	{
		return false;
	}
	
	/**
	 * The Chancellor will appear on the tile selected for 50ms
	 * @param tile
	 */
	public void Appears(Tile tile)
	{
		tile.addChancellor();
		if(isHit() == true)
		{
			
		}
		tile.removeChancellor();
	}
	
	
}

