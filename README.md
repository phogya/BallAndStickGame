# BallAndStickGame

A java program written for Parallel and Distributed Systems. It uses the TCP and a client-server model to play a game with multiple players. The game is called Ball and Stick. Players take turns removing either a ball or a stick, removing a ball removes all adjacent sticks. The player that removes the last piece, a ball or a stick, loses.

## Usage

Run BnsServer jar file from command line using:

	java -jar BnsServer.jar <host> <port>
	
where host and port are the host and port number, can use localhost as host
and 5678 as port.

Run BnsClient jar file from command line using:

	java -jar BnsClient.jar <host> <port> <name>
	
where host and port are the same as above and name is the player's name.

BnsServer must be run first to establish a server for players to play on and
it must be left running until the players are done. Each game requires two
players so BnsClient must be run twice to start a game (a game window will 
still appear for one player but they will not be able to play). The players
do not need to have unique names.