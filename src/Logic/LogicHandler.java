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

    public int[][] partitionsColumn(int threatCount, int columnCount) {
        int[][] range = new int[threatCount][2];

        int columnsPerThread = columnCount / threatCount;
        int remainder = columnCount % threatCount;

        int currentColumn = 0;
        for(int i = 0; i < threatCount; i++) {
            int start = currentColumn;
            int end = start + columnsPerThread - 1;

            if(remainder > 0) {
                end++;
                remainder--;
            }

            range[i][0] = start;
            range[i][1] = end;
            currentColumn = end + 1;
        }
        return range;
    }
    public void printWorkingThreads(int threadCount, Configuration config) {

        int colsCount = config.getySize();
        int rowsCount = config.getxSize();

        int [][] partitions = partitionsColumn(threadCount, colsCount);

        System.out.println("# " + threadCount + " threads, columns-based partitioning");

        for(int i = 0; i < threadCount; i++) {
            int start = partitions[i][0];
            int end = partitions[i][1];
            int columnCount = end - start + 1;

            System.out.println("tid " + i +": cols " + partitions[i][0] + ":" + partitions[i][1] + " (" + columnCount +  ") rows: 0:" + (rowsCount - 1) + " (" + rowsCount  + ")");
        }
    }
}