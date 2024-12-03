package Logic;

import Models.Board;
import Models.Configuration;
import Models.Coords;

import java.util.concurrent.CyclicBarrier;

public class ThreadWorker implements Runnable {
    private final CyclicBarrier barrier;
    private final int start;
    private final int end;
    private final LogicHandler logic;
    private final Board sharedBoard;

    public ThreadWorker(CyclicBarrier barrier, int start, int end, LogicHandler logic) {
        this.barrier = barrier;
        this.start = start;
        this.end = end;
        this.logic = logic;
        this.sharedBoard = logic.getSharedBoard();
    }

    @Override
    public void run() {
            try {
                Configuration config = logic.getSharedBoard().getConfig();
                int interations = config.getIterations();

                Board currentBoard = logic.getSharedBoard();
                Board tempBoard = new Board(currentBoard.getConfig().getxSize(), currentBoard.getConfig().getySize(), config);

                for (int i = 0; i < interations; i++) {
                    for (int j = start; j < end; j++) {
                        for (int k = 0; k < currentBoard.getConfig().getxSize(); k++) {
                            Coords newCoords = new Coords(j, k);
                            boolean isAlive = currentBoard.getCellState(newCoords);
                            int neighbours = currentBoard.countAllNeighbors(newCoords);

                            boolean newState = (isAlive && (neighbours == 2 || neighbours == 3)) || (!isAlive && neighbours == 3);
                            tempBoard.setCellState(newCoords, newState);
                        }
                    }
                    barrier.await();
                    synchronized (logic) {
                            logic.setSharedBoard(tempBoard);
                            //logic.getSharedBoard().printBoard();
                    }

                    //barrier.await();
                    if (barrier.getNumberWaiting() == 0) { // Sprawdzenie, czy wszystkie wątki zakończyły pracę
                        synchronized (logic) {
                            logic.getSharedBoard().printBoard();
                        }
                    }
                }
            }catch(Exception e ){
                e.printStackTrace();
            }
        }
    }