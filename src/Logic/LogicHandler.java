package Logic;

import Models.Board;
import Models.Configuration;

import java.util.concurrent.CyclicBarrier;

public class LogicHandler {

    private Board sharedBoard;

    public LogicHandler(Configuration config) {
        sharedBoard = new Board(config.getxSize(), config.getySize(), config);
    }

    public synchronized Board getSharedBoard() {
        return sharedBoard;
    }

    public synchronized void setSharedBoard(Board newBoard) {
        this.sharedBoard = new Board(newBoard);
    }

    public void runThreads(Configuration config, int threadCount) {
        int ySize = config.getySize();
        int[][] partitions = partitionsColumn(threadCount, ySize);

        CyclicBarrier barrier = new CyclicBarrier(threadCount, () -> {
            synchronized (sharedBoard) {
                sharedBoard.printBoard();
            }
        });

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            int start = partitions[i][0];
            int end = partitions[i][1];
            int iterations = config.getIterations();
            threads[i] = new Thread(new ThreadWorker(barrier, start, end, iterations, this));
            threads[i].start();
        }
        for (Thread thread : threads) {
            try{
                thread.join();
            }catch (InterruptedException e){
                System.err.println("Thread was interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    protected int[][] partitionsColumn(int threadCount, int columnCount) {
        int[][] range = new int[threadCount][2];

        int columnsPerThread = columnCount / threadCount;
        int remainder = columnCount % threadCount;

        int currentColumn = 0;
        for(int i = 0; i < threadCount; i++) {
            int start = currentColumn;
            int end = start + columnsPerThread -1;

            if(remainder > 0) {
                end++;
                remainder--;
            }

            if (end >= columnCount) {
                end = columnCount - 1;
            }
            range[i][0] = start;
            range[i][1] = end;
            currentColumn = end + 1;


            if (currentColumn >= columnCount) break;
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
            int columnCount;
            if (start <= end) {
                columnCount = end - start + 1;
            } else {
                columnCount = 0;
            }
            System.out.println("tid " + i +": cols " + start + ":" + end + " (" + columnCount +  ") rows: 0:" + (rowsCount - 1) + " (" + rowsCount  + ")");
        }
    }
}
