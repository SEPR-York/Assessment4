package com.mygdx.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.util.ArrayList;

/**
 * This class handles the back end for the leaderboard.
 * @author Gandhi-Inc.
 * @version Assessment 3
 *          An executable version of the game can be found at: http://gandhi-inc.me/downloads/assessment3.jar
 *          Our website is: www.gandhi-inc.me
 * @since Assessment 3
 */

public class LeaderboardBackend{

	// Declation and static assignment of the ArrayList to store the scores
	private ArrayList<String[]> ArrayOfPeopleWithScores = new ArrayList<String[]>();


	/**
	* Returns the array list of the leaderboard scores
	*
	* @return ArrayList<String[]> it returns an array list of string arrays where each string array is 2 items big with index 0 being the name, and index 1 being the score
	*/
	public ArrayList<String[]> getListofScores(){
		return ArrayOfPeopleWithScores;
	}

	/**
	 * This method handles adding new players to the end of the Game save file
	 *
	 * @param player - This is the name of the player that you want to store(type String)
	 * @param score - the score of the player that you want to store (type int)
	 */
	static public void AddPlayerToLeaderboard(String player,int score){
		try
		{
			String filename = "GameSave.txt";
			// Open a file write object to save the input data
		    FileWriter fw = new FileWriter(filename,true);
			// Write the player and the score and then a new line character at the end
			fw.write(player + "," + Integer.toString(score) + ",\n");
			// Close the file when finished
		    fw.close();
		}
		catch(IOException ioe)
		{
			// If it fails at any point write the exception information to the screen for debugging
		    System.err.println("IOException: " + ioe.getMessage());
		}
	}

	/**
	 * This method opens the GameSave.txt file, splits the vaules held in the file
	 * and the populates the array list "arrayofpeoplewithscores".
	 * <p>
	 * This method also contains error handling for if the file "GameSave.txt" does not exist.
	 * If the file does not exist, the method will create the file and populate it with
	 * Example players.
	 */
	public void OpenFile(){


		BufferedReader br;														// Declare the buffered reader (To Read the file)
		try{
			br = new BufferedReader(new FileReader("GameSave.txt"));			// Assign it given the file path
		} catch (Exception e){													//Catches the error if the file does not exist
			File f = new File("./GameSave.txt");
			try
			{
				f.createNewFile();												//creates the new file
			}
			catch (Exception err)
			{
				err.printStackTrace();											//if a new file cannot be created an error is raised.
				return;
			}
			AddPlayerToLeaderboard("Example Player 1",0);						//populates the file with example players with a score of 0
			AddPlayerToLeaderboard("Example Player 2",0);
			AddPlayerToLeaderboard("Example Player 3",0);
			try
			{
				br = new BufferedReader(new FileReader("GameSave.txt"));		//opens the newly created file
			}
			catch (Exception err)
			{
				err.printStackTrace();											//if for some reason this fails then catch the exception and print the stack trace for debugging (In reality should never fail)
				return;
			}
		}


		String line;															// Declare string to store current line

		try{
			line = br.readLine();												// Attempt to read the line from file
		} catch (Exception e){													//Catches error if cannot read line
			e.printStackTrace();
			return;
		}

		while (line != null){													// While there is a line to read add it to the ArrayList
			ArrayOfPeopleWithScores.add(line.split(","));						//splits the file up on a comma
			try{
				line = br.readLine();											// Attempt to read the next line
			} catch (Exception e){
				e.printStackTrace();
				return;
			}
		}

		try{
			br.close();															//tries to close the file
		} catch (Exception e){
			e.printStackTrace();
			return;
		}
	}
	/**
	 * This function will return the best three players that have played the game so that they are able to be displayed
	 * <p>
	 * This method also contains error handling for if the "GameSave.txt" file contains less than three players
	 *
	 * @return ArrayOfBestPlayers - This is an array of size three that contains the best three players
	 */
	private int ReturnBestPlayer(ArrayList<String[]> a){
		// Copy the input array
		ArrayList<String[]> AllThePlayers = new ArrayList<String[]>(a);
		// Initialise the index of the current highest and the value of the current highest
		int index = 0;
		int highest = 0;
		for (int i = 0; i < AllThePlayers.size(); i++){
			// If the score isn't stored for the current player then delete the player
			if (AllThePlayers.get(index).length == 1)
			{
				String[] retArray = new String[2];
				retArray[0] = "";
				retArray[1] = "";
				AllThePlayers.set(index, retArray);
			}
			int tmp;
			try
			{
				// Assign tmp to the integer value of score (converts the string to int)
				tmp = Integer.parseInt(AllThePlayers.get(i)[1]);
			}
			catch (Exception e)
			{
				// Otherwise initialise to 0
				tmp = 0;
				e.printStackTrace();
			}
			// If the current score is higher than the highest score then replace the index with the current index and the highest value with the current highest
			if (tmp > highest){
				index = i;
				highest = tmp;
			}
		}
		// return the index of the player with the highest score
		return(index);
	}

	/**
	* A function that that returns the players names and the scores that have the top three highest scores.
	* @return 2 dimensional string array where on dimension is the players names and the other is the players scores
	*/
	public String[][] GetTopThree(){
		// Copy the array list so that no data is lost
		ArrayList<String[]> AllThePlayers = new ArrayList<String[]>(getListofScores());
		// initalise the return array
		String[][] retArray = new String[3][];
		// Initalise all rows in the array to be valid return arrays
		for (int i = 0; i < 3; i++)
		{
			retArray[i] = new String[2];
			retArray[i][0] = "";
			retArray[i][1] = "";
		}
		// Do this loop until 3 players have been filled into the return array or there are no more players in the list of all scores
		for (int i = 0; i < 3 && AllThePlayers.size() != 0; i++)
		{
			// get the current best player from the players left in the list
			int index = ReturnBestPlayer(AllThePlayers);
			// if the return has an array of length then fill the delete the player
			if (AllThePlayers.get(index).length == 1)
			{
				String[] tmpArray = new String[2];
				tmpArray[0] = "";
				tmpArray[1] = "";
				AllThePlayers.set(index, tmpArray);
			}
			// Then add the player to the return array
			retArray[i] = AllThePlayers.get(index);

			// And remove the player from the list so that they are not able to be picked again
			AllThePlayers.remove(index);
		}

		// return the array
		return retArray;
	}

}
