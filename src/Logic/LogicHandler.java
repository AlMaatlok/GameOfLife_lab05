package Logic;

import Models.Board;
import Models.Configuration;
import Models.Coords;

public class LogicHandler {

    private static Board sharedBoard;

    public LogicHandler(Configuration config) {
        sharedBoard = new Board(config.getxSize(), config.getySize(), config);
    }

    public synchronized Board getSharedBoard() {
        return sharedBoard;
    }

    public synchronized void setSharedBoard(Board newBoard) {
        sharedBoard.updateBoard(newBoard);
    }

    public void goingThroughIterations(Configuration config, Board board) {
        int iterations = config.getIterations();
        int xSize = config.getxSize();
        int ySize = config.getySize();

        for (int i = 0; i < iterations; i++) {
            Board tempBoard;

            synchronized (this) {
                tempBoard = new Board(xSize, ySize, config);
                Board currentBoard = getSharedBoard();

                for (int j = 0; j < xSize; j++) {
                    for (int k = 0; k < ySize; k++) {
                        Coords newCoords = new Coords(j, k);
                        boolean isAlive = currentBoard.getCellState(newCoords);
                        int neighbours = currentBoard.countAllNeighbors(newCoords);

                        boolean newState = (isAlive && (neighbours == 2 || neighbours == 3)) || (!isAlive && neighbours == 3);
                        tempBoard.setCellState(newCoords, newState);
                    }
                }
            }

            synchronized (this) {
                setSharedBoard(tempBoard);
            }

            synchronized (this) {
                sharedBoard.printBoard();
            }
        }
    }
}