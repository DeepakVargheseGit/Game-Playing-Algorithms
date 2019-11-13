import java.util.*;

/**
 * This is the AiPlayer class.  It simulates a minimax player for the max
 * connect four game.
 * The constructor essentially does nothing. 
 * 
 * @author james spargo
 *
 */

public class AiPlayer 
{
	/**
	 * The constructor essentially does nothing except instantiate an
	 * AiPlayer object.
	 *
	 */
	public AiPlayer() 
	{
		// nothing to do here
	}

	int opt_value;

	/**
	 * This method plays a piece randomly on the board
	 * @param currentGame The GameBoard object that is currently being used to
	 * play the game.
	 * @return an integer indicating which column the AiPlayer would like
	 * to play in.
	 */
	public int findBestPlay( GameBoard currentGame, int depthLevel ) //this function is used to get the best move in the current game the computer can make using the min-max algorithm
	{

		int alpha = Integer.MIN_VALUE ;
		int beta  = Integer.MAX_VALUE;
		int value = Integer.MIN_VALUE;
		int playChoice = 99;

		for (int i = 0; i <7 ;i++)
		{
			if(currentGame.isValidPlay(i))
			{
				GameBoard next_made_move = new GameBoard(currentGame.getGameBoard());  //used to check all the possible moves in current game
				next_made_move.playPiece(i);
				//possible_move.add(next_made_move);
				opt_value = Min_value(next_made_move, alpha , beta, depthLevel-1);

				if (opt_value > value )
				{
					value = opt_value;
					playChoice = i;
				}
			} 
		}
		return playChoice;
		//opt_value = Max_value(currentGame, alpha , beta, depthLevel);

	}

	public int Max_value(GameBoard currentGame, int alpha, int beta, int depthLevel) //implements the max part of the min-max algorithm 
	{
		if (currentGame.getPieceCount() == 42 )
		{
			return Utility(currentGame);
		}
		if(depthLevel == 0)
		{
			return Eval(currentGame);
		}
		else
		{
			int opt_value = Integer.MIN_VALUE;

			ArrayList<GameBoard> move_state = new ArrayList<GameBoard>();
			move_state = next_move(currentGame);
			for(int i = 0; i < move_state.size()  ; i++)
			{
				opt_value = Math.max(opt_value,Min_value(move_state.get(i), alpha , beta,depthLevel-1));

				if (opt_value >= beta)
				{
					return opt_value;
				}
				alpha = Math.max(alpha,opt_value);		
			}
			return opt_value;
		}
	}

	public int Min_value(GameBoard currentGame, int alpha, int beta, int depthLevel)  //implements the min part of the min-max algorithm 
	{
		if (currentGame.getPieceCount() == 42)
		{
			return Utility(currentGame);
		}
		if(depthLevel == 0)
		{
			return Eval(currentGame);
		}
		else
		{
			int opt_value = Integer.MAX_VALUE;
			ArrayList<GameBoard> move_state = new ArrayList<GameBoard>();
			move_state = next_move(currentGame);
			for(int i = 0; i < move_state.size() ; i++)
			{
				opt_value = Math.min(opt_value,Max_value(move_state.get(i), alpha , beta,depthLevel-1));
				if (opt_value <= alpha)
				{
					return opt_value;
				}
				beta = Math.min(beta,opt_value);			
			}
			return opt_value;
		}
	}

	public int Utility (GameBoard currentGame)  //this function returns the utility value when the terminal state has been reached 
	{
		if(currentGame.getScore( 1 ) > currentGame.getScore( 2 ))
		{
			int utility_value = Integer.MAX_VALUE;
			return utility_value;
		}
		else if(currentGame.getScore( 2 ) > currentGame.getScore( 1 ))
		{
			int utility_value = Integer.MIN_VALUE;
			return utility_value;
		}
		else {
			return 0;
		}

	}

	public ArrayList<GameBoard> next_move(GameBoard currentGame) //returns all the possible moves that can be made 
	{
		ArrayList<GameBoard> possible_move = new ArrayList<GameBoard>();

		for (int i = 0; i<7 ; i++)
		{
			if(currentGame.isValidPlay(i))
			{
				GameBoard next_made_move = new GameBoard(currentGame.getGameBoard());
				next_made_move.playPiece(i);
				possible_move.add(next_made_move);
			}
		}
		return possible_move;
	}
	public int Eval(GameBoard currentGame)  // Evaluation function 
	{
			return currentGame.getScore( 1 ) - currentGame.getScore( 2 );
	}
}

// Possible eval function implementation which was used initially . 
		/* int eval1;
		int eval2;
		int eval3;
		int eval4;
		int player = currentGame.getCurrentTurn();
		GameBoard current_board = new GameBoard(currentGame.getGameBoard());

		int[][] playBoard;

		playBoard = currentGame.getGameBoard();

		int evalfinal = 0;

		//For 3 dots together 

		//check horizontally
		for( int i = 5; i >= 0; i++ ) 
		{
			for( int j = 0; j < 4; j++ ) 
			{


				if (i <5)
				{
					if( ( playBoard[ i ][j] == player )     &&
							( playBoard[ i ][ j+1 ] == player ) &&
							( playBoard[ i ][ j+2 ] == player ) &&
							( playBoard[ i ][ j+3 ] == 0 )      &&
							( playBoard[ i+1 ][ j+3 ] != 0 )       ) 
					{
						eval1 = 20;
					}
					else if( ( playBoard[ i ][j] == 0 )     &&
							( playBoard[ i ][ j+1 ] == player ) &&
							( playBoard[ i ][ j+2 ] == player ) &&
							( playBoard[ i ][ j+3 ] == player )      &&
							( playBoard[ i+1 ][ j ] != 0 )       )
					{
						eval1 = 20;
					}
					else
					{
						return currentGame.getScore( 1 ) - currentGame.getScore( 2 );
					}
				}
				else
				{
					if( ( playBoard[ i ][j] == player )     &&
							( playBoard[ i ][ j+1 ] == player ) &&
							( playBoard[ i ][ j+2 ] == player ) &&
							( playBoard[ i ][ j+3 ] == 0 ) ) 
					{
						eval1 = 20;
					}
					else if( ( playBoard[ i ][j] == 0 )     &&
							( playBoard[ i ][ j+1 ] == player ) &&
							( playBoard[ i ][ j+2 ] == player ) &&
							( playBoard[ i ][ j+3 ] == player ) )
					{
						eval1 = 20;
					}
					else
					{
						return currentGame.getScore( 1 ) - currentGame.getScore( 2 );
					}


				}


			}
		} // end horizontal

		//check vertically
		for( int i = 0; i < 3; i++ ) {
			for( int j = 0; j < 7; j++ ) {
				if( ( playBoard[ i ][ j ] == 0 ) &&
						( playBoard[ i+1 ][ j ] == player ) &&
						( playBoard[ i+2 ][ j ] == player ) &&
						( playBoard[ i+3 ][ j ] == player ) ) {
					eval1 = 20;
				}
			}
		} // end verticle	


		//check diagonally - backs lash ->	\
		for( int i = 0; i < 3; i++ ){
			for( int j = 0; j < 4; j++ ) {

				if(i<2)
				{
					if(     ( playBoard[ i ][ j ] == 0 )          &&
							( playBoard[ i+1 ][ j+1 ] == player ) &&
							( playBoard[ i+2 ][ j+2 ] == player ) &&
							( playBoard[ i+3 ][ j+3 ] == player ) &&
							( playBoard[ i+1 ][ j ] != 0 ))
					{
						eval1 = 20;
					}
					else if(( playBoard[ i ][ j ] == player )     &&
							( playBoard[ i+1 ][ j+1 ] == player ) &&
							( playBoard[ i+2 ][ j+2 ] == player ) &&
							( playBoard[ i+3 ][ j+3 ] == 0 )      &&
							( playBoard[ i+4 ][ j+3 ] != 0 ))
					{
						eval1 = 20;
					}
					else
					{
						return currentGame.getScore( 1 ) - currentGame.getScore( 2 );
					}
				}
				else
				{
					if(     ( playBoard[ i ][ j ] == 0 )          &&
							( playBoard[ i+1 ][ j+1 ] == player ) &&
							( playBoard[ i+2 ][ j+2 ] == player ) &&
							( playBoard[ i+3 ][ j+3 ] == player ) &&
							( playBoard[ i+1 ][ j ] != 0 ))
					{
						eval1 = 20;
					}
					else if(( playBoard[ i ][ j ] == player )     &&
							( playBoard[ i+1 ][ j+1 ] == player ) &&
							( playBoard[ i+2 ][ j+2 ] == player ) &&
							( playBoard[ i+3 ][ j+3 ] == 0 ))
					{
						eval1 = 20;
					}
					else
					{
						return currentGame.getScore( 1 ) - currentGame.getScore( 2 );
					}
				}
			}
		} // end backslash diagonal 	

		 //check diagonally - forward slash -> /
	    for( int i = 0; i < 3; i++ ){
		for( int j = 0; j < 4; j++ ) {
			
			if(i<2)
			{
		    if( ( playBoard[ i+3 ][ j ] == player ) &&
			    ( playBoard[ i+2 ][ j+1 ] == player )   &&
			    ( playBoard[ i+1 ][ j+2 ] == player )   &&
			    ( playBoard[ i ][ j+3 ] == 0 )     &&
			    (playBoard[ i+1 ][ j+3 ] != 0 )               )
		    {
		    	eval1 = 20;
		    }
		    else if ( ( playBoard[ i+3 ][ j ] == 0 ) &&
					( playBoard[ i+2 ][ j+1 ] == player ) &&
					( playBoard[ i+1 ][ j+2 ] == player ) &&
					( playBoard[ i ][ j+3 ] == player )   &&
					( playBoard[ i+3 ][ j ] != 0 )      )
		    {
		    	
		    }
		    else
		    {
		    	return currentGame.getScore( 1 ) - currentGame.getScore( 2 );
		    }
		    }
			else
			{
				if( ( playBoard[ i+3 ][ j ] == player ) &&
					    ( playBoard[ i+2 ][ j+1 ] == player )   &&
					    ( playBoard[ i+1 ][ j+2 ] == player )   &&
					    ( playBoard[ i ][ j+3 ] == 0 )     &&
					    (playBoard[ i+1 ][ j+3 ] != 0 )               )
				    {
				    	eval1 = 20;
				    }
				    else if ( ( playBoard[ i+3 ][ j ] == 0 ) &&
							( playBoard[ i+2 ][ j+1 ] == player ) &&
							( playBoard[ i+1 ][ j+2 ] == player ) &&
							( playBoard[ i ][ j+3 ] == player ) )
				    {
				    	
				    }
				    else
				    {
				    	return currentGame.getScore( 1 ) - currentGame.getScore( 2 );
				    }	
			}
			
			
		}
	    }// end player score check
		return evalfinal;
	}
  */

	
