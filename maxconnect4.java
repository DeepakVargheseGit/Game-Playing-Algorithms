import java.io.*;
import java.util.Scanner;

/**
 * 
 * @author James Spargo
 * This class controls the game play for the Max Connect-Four game. 
 * To compile the program, use the following command from the maxConnectFour directory:
 * javac *.java
 *
 * the usage to run the program is as follows:
 * ( again, from the maxConnectFour directory )
 *
 *  -- for interactive mode:
 * java MaxConnectFour interactive [ input_file ] [ computer-next / human-next ] [ search depth]
 *
 * -- for one move mode
 * java maxConnectFour.MaxConnectFour one-move [ input_file ] [ output_file ] [ search depth]
 * 
 * description of arguments: 
 *  [ input_file ]
 *  -- the path and filename of the input file for the game
 *  
 *  [ computer-next / human-next ]
 *  -- the entity to make the next move. either computer or human. can be abbreviated to either C or H. This is only used in interactive mode
 *  
 *  [ output_file ]
 *  -- the path and filename of the output file for the game.  this is only used in one-move mode
 *  
 *  [ search depth ]
 *  -- the depth of the minimax search algorithm
 * 
 *   
 */

public class maxconnect4
{
	public static void main(String[] args) 
	{
		// check for the correct number of arguments
		if( args.length != 4 ) 
		{
			System.out.println("Four command-line arguments are needed:\n"
					+ "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n"
					+ " or:  java [program name] one-move [input_file] [output_file] [depth]\n");

			exit_function( 0 );
		}

		// parse the input arguments
		String game_mode = args[0].toString();				// the game mode
		String input = args[1].toString();					// the input game file
		int depthLevel = Integer.parseInt( args[3] );  		// the depth level of the ai search

		// create and initialize the game board
		GameBoard currentGame = new GameBoard( input );

		// create the Ai Player
		AiPlayer calculon = new AiPlayer();

		//  variables to keep up with the game
		int playColumn = 99;				//  the players choice of column to play
		boolean playMade = false;	//  set to true once a play has been made
		String output = args[2].toString();				// the output game file
		Scanner in_Scanner = new Scanner(System.in); //used to get inputs from the user using scanner

		String current_player_Interactive = output;
		if( game_mode.equalsIgnoreCase( "interactive" ) ) //checks if the game mode given is interactive 
		{
			System.out.println("\nInteractive game\n");	
			
			while(currentGame.getPieceCount() < 42)  //checks if the piececount is less than 42
			{
				System.out.print("game state before move:\n");
				currentGame.printGameBoard();
				System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
						", Player2 = " + currentGame.getScore( 2 ) + "\n " );
				//check if its comp or user

				if (current_player_Interactive.equalsIgnoreCase("human-next")) //uses this logic to let user input the column it wants to make its move
				{
					currentGame.setCurrentTurn(2);
					System.out.println("Enter your column:");
					int column_input = in_Scanner.nextInt()-1;
					if( currentGame.getPieceCount() < 42 ) 
					{

						while(!(currentGame.isValidPlay(column_input))) // checks if the column provided is a valid move that can be made 
						{
							System.out.println("Sorry the entered column is not valid..Enter a new column:");
							 column_input = in_Scanner.nextInt()-1;
						}
						currentGame.playPiece( column_input );

						currentGame.printGameBoardToFile( "human.txt" );
						current_player_Interactive = "computer-next" ;
					} 
					else 
					{
						System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over"); //if piececount is 42 then no more moves can be made and the game is over
					}
				}
				else //uses the findbest play logic used to find the optimal move for the computer to make 
				{

					
					// ****************** this chunk of code makes the computer play
					if( currentGame.getPieceCount() < 42 ) 
					{
						currentGame.setCurrentTurn(1);
						// AI play - random play
						playColumn = calculon.findBestPlay( currentGame, depthLevel  ); //provides the best possible move for the computer when it has to make a move

						// play the piece
						currentGame.playPiece( playColumn );

						currentGame.printGameBoardToFile("computer.txt");
						current_player_Interactive = "human-next" ;
					} 
					else 
					{
						System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over");
					}
				}
			}
			currentGame.printGameBoard(); // Prints the board and shows who won the game. 
			System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
					", Player2 = " + currentGame.getScore( 2 ) + "\n " );
			if(currentGame.getScore( 1 ) > currentGame.getScore( 2 ))
			{
				System.out.println("Computer wins the game");
			}
			else if(currentGame.getScore( 1 ) < currentGame.getScore( 2 ))

			{
				System.out.println("Human wins the game");
			}
			else {
				System.out.println("The game ends in a tie ");

			}
			return;

		} 

		else if( !game_mode.equalsIgnoreCase( "one-move" ) ) 
		{
			System.out.println( "\n" + game_mode + " is an unrecognized game mode \n try again. \n" );
			return;
		}

		/////////////   one-move mode ///////////
		// get the output file name

		//this logic is used when the game mode is one move mode 
		
		
		System.out.print("\nMaxConnect-4 game\n");
		System.out.print("game state before move:\n");

		//print the current game board
		currentGame.printGameBoard();
		// print the current scores
		System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
				", Player2 = " + currentGame.getScore( 2 ) + "\n " );

		
		if( currentGame.getPieceCount() < 42 ) 
		{
			int current_player = currentGame.getCurrentTurn();
			if(current_player == 2) // ****************** this chunk of code lets the user to give input for one move mode 
			{
				System.out.println("Enter your column:");
				int column_input = in_Scanner.nextInt()-1;
				
				while(!(currentGame.isValidPlay(column_input))) {
					System.out.println("Sorry the entered column is not valid..Enter a new column:");
					 column_input = in_Scanner.nextInt()-1;
				}
				currentGame.playPiece( column_input );
				
				playColumn = column_input+1;
				// display the current game board
				System.out.println("move " + currentGame.getPieceCount() 
				+ ": Player " + current_player
				+ ", column " + playColumn);
				System.out.print("game state after move:\n");
				currentGame.printGameBoard();

				// print the current scores
				System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
						", Player2 = " + currentGame.getScore( 2 ) + "\n " );

				currentGame.printGameBoardToFile( output );
			}
			else   // ****************** this chunk of code makes the computer play in one move mode 
			{
			int current_player_pc = currentGame.getCurrentTurn();
			// AI play - random play
			playColumn = calculon.findBestPlay( currentGame,depthLevel );

			// play the piece
			currentGame.playPiece( playColumn );

			// display the current game board
			System.out.println("move " + currentGame.getPieceCount() 
			+ ": Player " + current_player_pc
			+ ", column " + playColumn);
			System.out.print("game state after move:\n");
			currentGame.printGameBoard();

			// print the current scores
			System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
					", Player2 = " + currentGame.getScore( 2 ) + "\n " );

			currentGame.printGameBoardToFile( output );
			}
		} 
		else 
		{
			System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over");
		}

		//************************** end computer play


		return;

	} // end of main()

	/**
	 * This method is used when to exit the program prematurely.
	 * @param value an integer that is returned to the system when the program exits.
	 */
	private static void exit_function( int value )
	{
		System.out.println("exiting from MaxConnectFour.java!\n\n");
		System.exit( value );
	}
} // end of class connectFour
