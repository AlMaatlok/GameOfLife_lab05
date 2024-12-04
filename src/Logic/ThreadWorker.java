package Logic;

import Models.Board;
import Models.Cell;
import Models.Coords;

import java.util.concurrent.CyclicBarrier;

public class ThreadWorker extends Thread {

    private final CyclicBarrier barrier;
    private int start;
    private int end;
    private int iterationCount;

    private final LogicHandler logic;

    public ThreadWorker(CyclicBarrier barrier, int start, int end, int iterationCount, LogicHandler logic) {
        this.barrier = barrier;
        this.start = start;
        this.end = end;
        this.iterationCount = iterationCount;
        this.logic = logic;
    }


    @Override
    public void run() {
        for(int i = 0; i < iterationCount; i++) {

            Board tempBoard = new Board(logic.getSharedBoard().getConfig().getxSize(),
                    logic.getSharedBoard().getConfig().getySize(),
                    logic.getSharedBoard().getConfig());
            Cell[][] changedColumns = new Cell[end - start + 1][];

            for (int j = start; j <= end; j++) {
                Cell[] column = logic.getSharedBoard().getColumn(j);
                for (int k = 0; k < column.length; k++) {
                    Coords newCoords = new Coords(k, j);
                    boolean isAlive = column[k].getIsAlive();
                    int neighbours = logic.getSharedBoard().countAllNeighbors(newCoords);

                    boolean newState = (isAlive && (neighbours == 2 || neighbours == 3)) || (!isAlive && neighbours == 3);
                    tempBoard.setCellState(newCoords, newState);

                }

                changedColumns[j - start] = column;
            }
            try {
                barrier.await();

                synchronized (logic) {
                    logic.setSharedBoard(tempBoard);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}