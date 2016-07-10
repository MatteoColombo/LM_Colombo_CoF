# Council of Four #

## Contributors ##

* Matteo Colombo - 808660
* Gianpaolo Branca - 806826
* Davide Cavallini - 808552

## Implemented Functionalities ##

* Complete Rules
* CLI & GUI
* Socket & RMI
* Advanced: Automatic game configuration

## How do I get set up? ##

* #### Server setup ####
Run the server.Server.java class with no arguments
* #### GUI Client setup ####
Run the client.Client.java class with no arguments
* #### ClI Client setup ####
Run the client.Client.Java class with "-cli" as argument   
(client.Client.java -cli)

## How do I play with the CLI? ##

### Main Actions ###
* #### Slide council ####
slide -council [number 1-n or "k" for king] -color [english name]
* #### Buy permission card ####
permission -region [number 1-n] -card [1/2] -politic [politic card number]*  
* #### Build an emporium ####
emporium -city [city name] -card [1-n]
* #### Build an emporium with the king ####
king -city [city name] -politic [1-n]

### Secondary Actions ###
* #### Slide with assistant ####
secondarySlide -council [number 1-n or "k" for king] -color [english name]
* #### Shuffle permission cards of a region ####
shuffle -region [1-n]
* #### Extra Main Action ####
extra
* #### Buy assistant ####
assistant

### End turn ###
end

To end the turn, every main action must be done.
If a player skips a turn, he is disconnected.

## How do I play with the GUI? ##
* Press the button of the action that you want to do
** In case cards or councilors are needed, you have to drag and drop them on the item that you want to buy or on the city where you want to build


