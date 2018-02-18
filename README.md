# kalah
Kalah game

An example of [Kalah][1] game implementation consisting of a REST service endpoint.

Could be started by:
* Running the main() method.
* By utilizing [Maven exec plugin][2] with command `mvn clean compile exec:java`

Then, just access `http://localhost:8080/` 
 
 In order to start a new game run `http://localhost:8080/newGame`. Service will create a new instance of game and return data required to make moves.
 
 In order to make a move run:
 
 `http://localhost:8080/move?game_id=<id>&player_id=<player_id>&start_pit=<start_pit>`

 In order to close game session run:
 `http://localhost:8080/endGame?game_id=<id>`
 
 [1]: https://en.wikipedia.org/wiki/Kalah
 [2]: http://www.mojohaus.org/exec-maven-plugin/
