default: cpsc2150/extendedConnects/GameScreen.java cpsc2150/extendedConnectX/models/AbsGameBoard.java cpsc2150/extendedConnectX/models/IGameBoard.java cpsc2150/extendedConnectX/models/GameBoard.java cpsc2150/extendedConnectX/models/GameBoardMem.java cpsc2150/extendedConnectX/models/BoardPosition.java
	javac cpsc2150/extendedConnects/GameScreen.java cpsc2150/extendedConnectX/models/AbsGameBoard.java cpsc2150/extendedConnectX/models/IGameBoard.java cpsc2150/extendedConnectX/models/GameBoard.java cpsc2150/extendedConnectX/models/GameBoardMem.java cpsc2150/extendedConnectX/models/BoardPosition.java

run: cpsc2150/extendedConnects/GameScreen.class cpsc2150/extendedConnectX/models/AbsGameBoard.class cpsc2150/extendedConnectX/models/IGameBoard.class cpsc2150/extendedConnectX/models/GameBoard.class cpsc2150/extendedConnectX/models/GameBoardMem.class cpsc2150/extendedConnectX/models/BoardPosition.class
	java cpsc2150.extendedConnects.GameScreen

clean:
	rm -f cpsc2150/extendedConnectX/models/*.class
	rm -f cpsc2150/extendedConnects/*.class

test: cpsc2150/extendedConnects/GameScreen.java cpsc2150/extendedConnectX/models/AbsGameBoard.java cpsc2150/extendedConnectX/models/IGameBoard.java cpsc2150/extendedConnectX/models/GameBoard.java cpsc2150/extendedConnectX/models/GameBoardMem.java cpsc2150/extendedConnectX/models/BoardPosition.java cpsc2150/extendedConnectX/tests/TestGameBoard.java cpsc2150/extendedConnectX/tests/TestGameBoardMem.java
	javac -cp .:/usr/share/java/junit4.jar cpsc2150/extendedConnects/GameScreen.java cpsc2150/extendedConnectX/models/AbsGameBoard.java cpsc2150/extendedConnectX/models/IGameBoard.java cpsc2150/extendedConnectX/models/GameBoard.java cpsc2150/extendedConnectX/models/GameBoardMem.java cpsc2150/extendedConnectX/models/BoardPosition.java cpsc2150/extendedConnectX/tests/TestGameBoard.java cpsc2150/extendedConnectX/tests/TestGameBoardMem.java

testGB: cpsc2150/extendedConnects/GameScreen.class cpsc2150/extendedConnectX/models/AbsGameBoard.class cpsc2150/extendedConnectX/models/IGameBoard.class cpsc2150/extendedConnectX/models/GameBoard.class cpsc2150/extendedConnectX/models/GameBoardMem.class cpsc2150/extendedConnectX/models/BoardPosition.class cpsc2150/extendedConnectX/tests/TestGameBoard.class
	java -cp .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore cpsc2150.extendedConnectX.tests.TestGameBoard

testGBM: cpsc2150/extendedConnects/GameScreen.class cpsc2150/extendedConnectX/models/AbsGameBoard.class cpsc2150/extendedConnectX/models/IGameBoard.class cpsc2150/extendedConnectX/models/GameBoard.class cpsc2150/extendedConnectX/models/GameBoardMem.class cpsc2150/extendedConnectX/models/BoardPosition.class cpsc2150/extendedConnectX/tests/TestGameBoardMem.class
	java -cp .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore cpsc2150.extendedConnectX.tests.TestGameBoardMem