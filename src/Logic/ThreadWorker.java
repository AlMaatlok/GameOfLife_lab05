package Logic;

import Models.Board;
import Models.Coords;

import java.util.concurrent.CyclicBarrier;

class ThreadWorker implements Runnable {

    private final CyclicBarrier barrier;
    private final int start;
    private final int end;
    private final Board currentBoard;
    private final Board tempBoard;

    public ThreadWorker(CyclicBarrier barrier, int start, int end, Board currentBoard, Board tempBoard) {
        this.barrier = barrier;
        this.start = start;
        this.end = end;
        this.currentBoard = currentBoard;
        this.tempBoard = tempBoard;
    }

    @Override
    public void run() {
        for (int j = start; j <= end; j++) {
            for (int k = 0; k < currentBoard.getConfig().getxSize(); k++) {
                Coords newCoords = new Coords(k, j);
                boolean isAlive = currentBoard.getCellState(newCoords);
                int neighbours = currentBoard.countAllNeighbors(newCoords);

                boolean newState = (isAlive && (neighbours == 2 || neighbours == 3)) || (!isAlive && neighbours == 3);
                tempBoard.setCellState(newCoords, newState);
            }
        }

        try {
            barrier.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread was interrupted: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}