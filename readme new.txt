Name   - Deepak Varghese

The programming language used is Java.

The code is structured as following - 

1) Maxconnect4 

This file has the logic where the game is played from and in which mode its played from.
We have 2 modes in it - Interactive and one-move mode. 

Interactive mode takes input arguments and gives the option of entering the move when human has to play and 
makes the computers move using the findbestplay function.
The results are displayed in console.

One move mode takes a move from player and saves the state when player has to play. When the computer has to play 
it uses the implemented logic and makes the move and saves the state.


2) Aiplayer

This file has different functions implemented in it - 

a) findBestPlay 

This function is used to implement the min-max logic which helps in getting the best possible move when the computer 
has to play.

b) Max_value

This function helps in implementing the max logic of the min-max algorithm.

c) Min_value

This function helps in implementing the min logic of the min-max algorithm.

d) Utility 

This function provides the utility value when it the terminal state is reached.

e) next_move

This function is used to show all the next possible valid moves when the computer has to play and takes the best move.

f) Eval

This function provides an evaluation value based on the current scores of the players. 


3) Gameboard 

This file is used to check the gameboard state and checks if valid moves are being made and calculates the score.


Compilation instructions 

javac maxconnect4.java
javac Aiplayer.java
javac GameBoard.java

Interactive mode - java maxconnect4 interactive [input_file] [computer-next/human-next] [depth]

One move mode    - java maxconnect4 one-move [input_file] [output_file] [depth]



References 

1) https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-1-introduction/
2) https://www.cs.cornell.edu/courses/cs312/2002sp/lectures/rec21.htm
3) http://www.baeldung.com/java-minimax-algorithm
4) https://github.com/arjunvekariyagithub/MaxConnect4
5) https://github.com/wordh/MaxConnect4-CSE-44-Assginment-2-AI
