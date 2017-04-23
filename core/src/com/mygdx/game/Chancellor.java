package com.mygdx.game;

import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

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
	public void appears(Tile tile, Player player, GameEngine engine) throws InterruptedException
	{
		System.out.println("appears (chancellor class)");
		tile.showchancellorTexture();						// Add the chancellor to the tile
		
		engine.getGameScreen().render(1/30);			// SHOULD RENDER THE SCREEN AGAIN?!
		
		long currentTime = System.currentTimeMillis();		// Calculate the current game time
		long endtime = System.currentTimeMillis() + 500;	// Calculate the game time + 500ms
		while (endtime - currentTime > 0)					// While the game counts 500ms 
		{
			if(isHit(tile) == true)							// Checks if the tile has been hit
			{
				player.setMoney(player.getMoney() + 20); 	// Finds the player's money and adds 20						// Add score to player
				// Exit the game
			}
			currentTime = System.currentTimeMillis();		// Updates the current game time
		}
		tile.hidechancellorTexture();						// Remove the chancellor from the tile
		engine.getGameScreen().render(1);
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
		
		int random = rand0.nextInt(2);
		
		
		if (random == 1)
		{
			try 
			{
				JOptionPane chancellorOptionPane = new JOptionPane("You need to catch the Chancellor! If you manage to catch him there is a prize!");
	        	JDialog chancellorDialog = chancellorOptionPane.createDialog("Catch the Chancellor!");
	        	chancellorDialog.setAlwaysOnTop(true);
	        	chancellorDialog.setVisible(true);
	        	
				for(int i = 0; i < 30; i++)
				{
					int randomTile = rand1.nextInt(16);
					Tile[] listOfTiles = engine.getTiles();
					Tile tile = listOfTiles[randomTile];
					System.out.println("Chancellor appears on tile " + tile.getID());
					appears(tile, player, engine);
				}
				
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
	}
}

