package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.*;

/**
 * GameEngine class is what is used to run the back-end of the game. It has all the main methods and 
 * @author Gandhi-Inc.
 * @version Assessment 4
 *          An executable version of the game can be found at: http://gandhi-inc.me/downloads/assessment4.jar
 *          Our website is: www.gandhi-inc.me
 */

// Changed in Assessment 3: Added so no more than one GameEngine can be instantiated at any one time.
public class GameEngine 
{
    private static GameEngine _instance;
    public static GameEngine getInstance() 
    {
        return _instance;
    }

    /**
     * Stores current game-state, enabling transitions between screens and external QOL drawing functions
     */
    private Game game;

    public Chancellor chancellorGame;

    /**
     * The game's engine only ever runs while the main in-game interface is showing, so it was designed to manipulate
     * elements (both visual and logical in nature) on that screen
     * It therefore requires access to the public methods in the GameScreen class, so instantiation in this class
     * is a necessity
     */
    private GameScreen gameScreen;

    /**
     * Stores data pertaining to the game's active players
     * For more information, check the "Player" class
     */
    public static Player[] players;

	/**
     * Holds the numeric getID of the player who's currently active in the game
     */
    public int currentPlayerID;

    /**
     * Holds the number of the phase that the game is currently in
     * Varies between 1 and 6
     */
    private int phase;

    /**
     * Timer used to dictate the pace and flow of the game
     * This has a visual interface which will be displayed in the top-left corner of the game-screen
     */
    private GameTimer timer;

    /**
     * Object providing QOL drawing functions to simplify visual construction and rendering tasks
     */
    private Drawer drawer;

    /**
     * Holds all of the data and the functions of the game's market
     * Also comes bundled with a visual interface which can be rendered on to the game's screen
     */
    public Market market;

    /**
     * Array holding the tiles to be laid over the map
     * Note that the tiles' visuals are encoded by the image declared and stored in the GameScreen class (and not here)
     */
    private Tile[] tiles;

    /**
     * Holds the data pertaining to the currently-selected tile
     */
    private Tile selectedTile;

    /**
     * Variable dictating whether the game is running or paused at any given moment
     */
    private State state;

    /**
     * An integer signifying the ID of the next roboticon to be created
     *
     */
    private int roboticonIDCounter = 0;


    // Added in Assessment 3: Added to keep track of trades, colleges and random events.
    // ---------------------------------------------------------------------------------
    private Array<Trade> trades;

    private ArrayList<RandomEvent> randomEvents = new ArrayList<RandomEvent>();
    // ---------------------------------------------------------------------------------

    /**
     * Constructs the game's engine. Imports the game's state (for direct renderer access) and the data held by the
     * GameScreen which this engine directly controls; then goes on to set up player-data for the game's players,
     * tile-data for the on-screen map and the in-game market for in-play manipulation. In the cases of the latter
     * two tasks, the engine directly interacts with the GameScreen object imported to it (as a parameter of this
     * constructor) so that it can draw the visual interfaces for the game's market and tiles directly to the game's
     * primary interface.
     *
     * @param game Variable storing the game's state
     * @param gameScreen The object encoding the in-game interface which is to be controlled by this engine
     */
    public GameEngine(Game game, GameScreen gameScreen) 
    {
        _instance = this;

        this.game = game;
        //Import current game-state to access the game's renderer

        this.gameScreen = gameScreen;
        //Bind the engine to the main in-game interface
        //Required to alter the visuals and logic of the interface directly through this engine

        drawer = new Drawer(this.game);
        //Import QOL drawing function

        players = new Player[3];
        currentPlayerID = 1;
        //Set up objects to hold player-data
        //Start the game such that player 1 makes the first move

        //Start the game in the first phase (of 6, which recur until all tiles are claimed)

        timer = new GameTimer(0, new TTFont(Gdx.files.internal("font/testfontbignoodle.ttf"), 120), Color.WHITE, new Runnable() {
            @Override
            public void run() {
                nextPhase(); // Timeout event
            }
        });
        //Set up game timer
        //Game timer automatically ends the current turn when it reaches 0

        tiles = new Tile[16];
        //Initialise data for all 16 tiles on the screen
        //This instantiation does NOT automatically place the tiles on the game's main interface

        for (int i = 0; i < 16; i++) {
            final int fi = i;
            final GameScreen gs = gameScreen;

            tiles[i] = new Tile(this.game, this, i + 1, 5, 5, 5, false, new Runnable() {
                @Override
                public void run() {
                    gs.selectTile(tiles[fi], true);
                    selectedTile = tiles[fi];
                }
            });
        }
        //Configure all 16 tiles with independent yields and landmark data
        //Also assign listeners to them so that they can detect mouse clicks

        
        //Instantiates the game's market and hands it direct renderer access

        state = State.RUN;
        //Mark the game's current play-state as "running" (IE: not paused)

   
        phase = 0;
        currentPlayerID = 0;
        trades = new Array<Trade>();

    }

    public Tile[] getTiles() 
    {
		return tiles;
	}

	public void selectTile(Tile tile) {
        selectedTile = tile;
    }

    /**
     * Advances the game's progress upon call
     * Acts as a state machine of sorts, moving the game from one phase to another depending on what phase it is
     * currently at when this method if called. If player 1 is the current player in any particular phase, then the
     * phase number remains and control is handed off to the other player: otherwise, control returns to player 1 and
     * the game advances to the next state, implementing any state-specific features as it goes.
     *
     * PHASE 1: Acquisition of Tiles
     * PHASE 2: Acquisition of Roboticons
     * PHASE 3: Placement of Roboticons
     * PHASE 4: Chancellor Game (50% chance)
     * PHASE 5: Production of Resources by Roboticons
     * PHASE 6: Market Trading
     */

    // Changed in Assessment 3: Refactored nextPhase() from giant if-else statement to switch statement.
    public void nextPhase() 
    {
        timer.stop();
        nextPlayer();
        System.out.println(players[currentPlayerID].getName() + " | Phase " + phase);

        
        market.refreshButtonAvailability();
        switch (phase) 
        {
            case 1:
			drawer.toggleButton(gameScreen.endTurnButton(), false, Color.GRAY);
                market.produceRoboticon();
                break;

            case 2:
                timer.setTime(0, 30);
                timer.start();
                drawer.toggleButton(gameScreen.endTurnButton(), true, Color.WHITE);
                break;

            case 3:
            	timer.setTime(0, 30);
                timer.start();
                break;
             
            // CHANCELLOR PHASE (NEW ASSESSMENT 4)    
            case 4:           	
//            	// Opens up a JOptionPane to inform the player that the mini game is about to take place
//    			JOptionPane chancellorOptionPane = new JOptionPane("You need to catch the Chancellor! If you manage to catch him there is a prize!");
//    	       	JDialog chancellorDialog = chancellorOptionPane.createDialog("Catch the Chancellor!");
//    	      	chancellorDialog.setAlwaysOnTop(true);		// Make sure that it appears on top and is visible
//    	       	chancellorDialog.setVisible(true);			// Set the dialog box to visible
           		timer.setTime(0, 15);						// Sets the timer to 15 seconds
               	timer.start();								// Starts the timer	
               	chancellor();								// Calls the chancellor method    
               	break;										// Break out of the phase

            case 5:
                if (chancellorGame != null)						// If the chancellor was playing
                {
                    System.out.println("Ending Mini Game!!");	// Print that the game is now over
                    chancellorGame.reset();						// Reset the game (remove from all tiles)
                    chancellorGame = null;						// Set the game to null
                }
                timer.setTime(0, 5);							// Set the timer to 5 seconds
                timer.start();									// Start the timer
                produceResource();								// Call the produce resources
                break;

            case 6:
                break;
        }

        if(checkGameEnd())
        {
            System.out.println("Someone won");
            gameScreen.showPlayerWin(players[getWinner()]);
        }

        gameScreen.updatePhaseLabel();
        market.refreshPlayers();
        market.setPlayerListPosition(0);
        market.refreshAuction();

        //If the upgrade overlay is open, close it when the next phase begins
        if (gameScreen.getUpgradeOverlayVisible()) 
        {
            gameScreen.closeUpgradeOverlay();
        }

        if (isCurrentlyAiPlayer()) 
        {
            AiPlayer aiPlayer = (AiPlayer)currentPlayer();
            aiPlayer.performPhase(this, gameScreen);
        } 
        else 
        {
            testTrade();
        }
    }

    // Added in Assessment 3: Added to keep track of random events.
    public void checkEventDurations() {

        Iterator<RandomEvent> randomEventIterator = randomEvents.iterator();

        while (randomEventIterator.hasNext()) {

            RandomEvent event = randomEventIterator.next();

            if (event.getDuration() == 0) {
                event.decDuration();
                event.eventHappen(false);
            }

            else if (event.getDuration() == event.getEventCooldown()) {
                randomEventIterator.remove();
            }

            else {
                event.decDuration();
            }

            System.out.println(randomEvents.toString());
        }
    }

    // Added in Assessment 3: Added to check whether a specific event is currently happening.
    public boolean eventCurrentlyHappening(Integer eventValue) {
        boolean eventHappened = false;
        HashMap<Integer, String> eventLookUp = new HashMap<Integer, String>();

        eventLookUp.put(0, "com.mygdx.game.Earthquake");
        eventLookUp.put(1, "com.mygdx.game.Malfunction");

        for (RandomEvent event : randomEvents) {
            System.out.println(event.getClass().getName());
            if ((event.getClass().getName() == eventLookUp.get(eventValue))) {
                eventHappened = true;
            }
        }

        return eventHappened;
    }

    private void chancellor()
    {
        System.out.println("Entered Game!!!");
        chancellorGame = new Chancellor(this);
    }
    
    // Added in Assessment 3: Added to select random events.
    private void selectRandomEvent() {
        Random random = new Random();
        int eventValue = random.nextInt(4);
        boolean eventHappened = eventCurrentlyHappening(eventValue);
        if (!eventHappened) {
            switch (eventValue) {
                case 0:
                    randomEvents.add(new Earthquake(this, gameScreen));
                    randomEvents.get(randomEvents.size() - 1).eventHappen(true);
                    break;
                case 1:
                    int playerToAffect = random.nextInt(players().length);
                    boolean playerHasRoboticons = false;

                    for (Tile tile: players()[playerToAffect].getTileList()) {
                        if (tile.getRoboticonStored() != null) {
                            playerHasRoboticons = true;
                        }
                    }

                    if (playerHasRoboticons) {
                        randomEvents.add(new Malfunction(this, gameScreen, playerToAffect));
                        randomEvents.get(randomEvents.size() - 1).eventHappen(true);
                    }

                    break;
            }
        }
    }

    private void produceResource() {
        Player player = currentPlayer();
        for (Tile tile : player.getTileList()) {
            tile.produce();
        }
    }

    /**
     * Sets the current player to be that which isn't active whenever this is called
     * Updates the in-game interface to reflect the statistics and the identity of the player now controlling it
     */
    private void nextPlayer() {
        currentPlayerID ++;
        if (currentPlayerID >= players.length) {
            currentPlayerID = 0;
 
            
            if (phase == 5) 
            {
                checkEventDurations();
                selectRandomEvent();
            }

            phase ++;
            if (phase > 6) 
            {
                phase = 1;
            }
            System.out.print("Move to phase " + phase + ", ");
        }
        System.out.println("Change to player " + players[currentPlayerID].getName());
        gameScreen.updatePlayerName();
        
        // Find and draw the icon representing the "new" player's associated college
        if (!isCurrentlyAiPlayer())
        {
	        gameScreen.currentPlayerIcon().setDrawable(new TextureRegionDrawable(new TextureRegion(players[currentPlayerID].getCollege().getLogoTexture())));
	        gameScreen.currentPlayerIcon().setSize(64, 64);
	
	        // Display the "new" player's inventory on-screen
	        gameScreen.updateInventoryLabels();
	
	    }
    }

    /**
     * Pauses the game and opens the pause-menu (which is just a sub-stage in the GameScreen class)
     * Specifically pauses the game's timer and marks the engine's internal play-state to [State.PAUSE]
     */
    public void pauseGame() {
        timer.stop();
        //Stop the game's timer

        gameScreen.openPauseStage();
        //Prepare the pause menu to accept user inputs

        state = State.PAUSE;
        //Mark that the game has been paused
    }

    /**
     * Resumes the game and re-opens the primary in-game inteface
     * Specifically increments the in-game timer by 1 second, restarts it and marks the engine's internal play-state
     * to [State.PAUSE]
     * Note that the timer is incremented by 1 second to circumvent a bug that causes it to lose 1 second whenever
     * it's restarted
     */
    public void resumeGame() {
        state = State.RUN;
        //Mark that the game is now running again

        gameScreen.openGameStage();
        //Show the main in-game interface again and prepare it to accept inputs

        if (timer.minutes() > 0 || timer.seconds() > 0) {
            timer.increment();
            timer.start();
        }
        //Restart the game's timer from where it left off
        //The timer needs to be incremented by 1 second before being restarted because, for a reason that I can't
        //quite identify, restarting the timer automatically takes a second off of it straight away
    }

    /**
     * Claims the last tile to have been selected on the main GameScreen for the active player
     * This grants them the ability to plant a Roboticon on it and yield resources from it for themselves
     * Specifically registers the selected tile under the object holding the active player's data, re-colours its
     * border for owner identification purposes and moves the game on to the next player/phase
     */
    public void claimTile() {
        if (phase == 1 && !selectedTile.isOwned()) {
            players[currentPlayerID].assignTile(selectedTile);
            //Assign selected tile to current player

            selectedTile.setOwner(players[currentPlayerID]);
            //Set the owner of the currently selected tile to be the current player

            switch (players[currentPlayerID].getCollege().getID()) {
                case 0:
                    //DERWENT
                    selectedTile.setTileBorderColor(Color.BLUE);
                    break;
                case 1:
                    //LANGWITH
                    selectedTile.setTileBorderColor(Color.CHARTREUSE);
                    break;
                case 2:
                    //VANBURGH
                    selectedTile.setTileBorderColor(Color.TEAL);
                    break;
                case 3:
                    //JAMES
                    selectedTile.setTileBorderColor(Color.CYAN);
                    break;
                case 4:
                    //WENTWORTH
                    selectedTile.setTileBorderColor(Color.MAROON);
                    break;
                case 5:
                    //HALIFAX
                    selectedTile.setTileBorderColor(Color.YELLOW);
                    break;
                case 6:
                    //ALCUIN
                    selectedTile.setTileBorderColor(Color.RED);
                    break;
                case 7:
                    //GOODRICKE
                    selectedTile.setTileBorderColor(Color.GREEN);
                    break;
                case 8:
                    //CONSTANTINE
                    selectedTile.setTileBorderColor(Color.PINK);
                    break;
            }
            //Set the colour of the tile's new border based on the college of the player who claimed it

            nextPhase(); // at ClaimTile
            //Advance the game
        }
    }

    /**
     * Deploys a Roboticon on the last tile to have been selected
     * Draws a Roboticon from the active player's Roboticon count and assigns it to the tile in question
     */
    public void deployRoboticon() {
        if (phase == 3) {
            if (!selectedTile.hasRoboticon()) {
                if (players[currentPlayerID].getRoboticonInventory() > 0) {
                    Roboticon Roboticon = new Roboticon(roboticonIDCounter, players[currentPlayerID], selectedTile);
                    selectedTile.assignRoboticon(Roboticon);
                    roboticonIDCounter += 1;
                    players[currentPlayerID].decreaseRoboticonInventory();
                    gameScreen.updateInventoryLabels();
                }
            }
        }
    }

    /**
     * Return's the game's current play-state, which can either be [State.RUN] or [State.PAUSE]
     * This is not to be confused with the game-state (which is directly linked to the renderer)
     *
     * @return State The game's current play-state
     */
    public State state() {
        return state;
    }

    /**
     * Return's the game's phase as a number between (or possibly one of) 1 and 6
     *
     * @return int The game's current phase
     */
    public int phase() {
        return phase;
    }

    /**
     * Returns all of the data pertaining to the array of players managed by the game's engine
     * Unless the game's architecture changes radically, this should only ever return two Player objects
     *
     * @return Player[] An array of all Player objects (encapsulating player-data) managed by the engine
     */
    public Player[] players() { return players; }

    /**
     * Returns the data pertaining to the player who is active at the time when this is called
     *
     * @return Player The current user's Player object, encoding all of their data
     */
    public Player currentPlayer() { return players[currentPlayerID]; }

    /**
     * Returns the ID of the player who is active at the time when this is called
     *
     * @return int The current player's ID
     */
    public int currentPlayerID() {
        return currentPlayerID;
    }

    /**
     * Returns the GameTimer declared and managed by the engine
     *
     * @return GameTimer The game's internal timer
     */
    public GameTimer timer() {
        return timer;
    }

    /**
     * Collectively returns every Tile managed by the engine in array
     *
     * @return Tile[] An array of all Tile objects (encapsulating tile-data) managed by the engine
     */
    public Tile[] tiles() {
        return tiles;
    }

    /**
     * Returns the data pertaining to the last Tile that was selected by a player
     *
     * @return Tile The last Tile to have been selected
     */
    public Tile selectedTile() {
        return selectedTile;
    }

    /**
     * Returns all of the data pertaining to the game's market, which is declared and managed by the engine
     *
     * @return Market The game's market
     */
    public Market market() {
        return market;
    }

    /**
     * Returns a value that's true if all tiles have been claimed, and false otherwise
     *
     * @return Boolean Determines if the game has ended or not
     */
    private boolean checkGameEnd(){
        for(Tile tile : tiles){
            if (tile.getOwner() == null){
                return false;
            }
        }

        return true;
    }

    /**
     * Updates the data pertaining to the game's current player
     * This is used by the Market class to process item transactions
     *
     * @param currentPlayer The new Player object to represent the active player with
     */
    public void updateCurrentPlayer(Player currentPlayer) {
        players[currentPlayerID] = currentPlayer;

        gameScreen.updateInventoryLabels();
        //Refresh the on-screen inventory labels to reflect the new object's possessions
    }

    /**
     * Function for upgrading a particular level of the roboticon stored on the last tile to have been selected
     * @param resource The type of resource which the roboticon will gather more of {0: ore | 1: energy | 2: food}
     */
    public void upgradeRoboticon(int resource) {
        if (selectedTile().getRoboticonStored().getLevel()[resource] == 0) {
            gameScreen.showEventMessage("The roboticon on this tile has malfunctioned!");
        }
         else if (selectedTile().getRoboticonStored().getLevel()[resource] < selectedTile().getRoboticonStored().getMaxLevel()) {
            switch (resource) {
                case (0):
                    currentPlayer().setMoney(currentPlayer().getMoney() - selectedTile.getRoboticonStored().getOreUpgradeCost());
                    break;
                case (1):
                    currentPlayer().setMoney(currentPlayer().getMoney() - selectedTile.getRoboticonStored().getEnergyUpgradeCost());
                    break;
                case (2):
                    currentPlayer().setMoney(currentPlayer().getMoney() - selectedTile.getRoboticonStored().getFoodUpgradeCost());
                    break;
            }

            selectedTile().getRoboticonStored().upgrade(resource);
        }
        //Upgrade the specified resource
        //0: ORE
        //1: ENERGY
        //2: FOOD
    }

    // Added in Assessment 3 from here down to EOF.

    public int getPhase() {
        return phase;
    }

    public int getWinner(){
        List<Player> playersList = Arrays.asList(players);
        Collections.sort(playersList, new Comparator<Player>() {
            @Override
            public int compare(Player a, Player b) {
                return b.calculateScore() - a.calculateScore();
            }
        });
        LeaderboardBackend.AddPlayerToLeaderboard(Integer.toString(playersList.get(0).getPlayerID()), playersList.get(0).calculateScore());

        return playersList.get(0).getPlayerID();
    }
    public boolean isCurrentlyAiPlayer() {
        return currentPlayer().isAi();
    }

    
    
    public void addTrade(Trade trade){
    	trades.add(trade);
    }

    public Trade getCurrentPendingTrade() {
        Iterator<Trade> it = trades.iterator();

        while (it.hasNext()) {
            Trade trade = it.next();
            if (trade.getTargetPlayer() == currentPlayer()) {
                it.remove();
                return trade;
            }
        }

        return null;
    }
    
    

    public void testTrade()
    {
        Trade trade = getCurrentPendingTrade();
        if (trade == null) return ;
        gameScreen.activeTrade(trade);
    }

    public void closeTrade()
    {
        gameScreen.closeTradeOverlay();
    }


    public void miniGame() 
    {
        game.setScreen(new MiniGameScreen());
    }

    public GameScreen getGameScreen() 
    {
        return gameScreen;
    }

    public Player[] getPlayers() 
    {
		return players;
	}

	public void setPlayers(Player[] players) 
	{
		GameEngine.players = players;
	}
	
    public void backToGame()
    {
        game.setScreen(getGameScreen());

    }
    /**
     * Encodes possible play-states
     * These are not to be confused with the game-state (which is directly linked to the renderer)
     */
    public enum State {
        RUN,
        PAUSE
    }
}
