package Logic;

import Models.Board;
import Models.Configuration;

import java.util.concurrent.CyclicBarrier;

public class LogicHandler {

    private static Board sharedBoard;
    boolean isMainThread = true;

    public LogicHandler(Configuration config) {
        sharedBoard = new Board(config.getxSize(), config.getySize(), config);
    }

    public synchronized Board getSharedBoard() {
        return sharedBoard;
    }

    public synchronized void setSharedBoard(Board newBoard) {
        sharedBoard.updateBoard(newBoard);
    }

    public void runThreads(Configuration config, int threadCount) {
        int ySize = config.getySize();
        int[][] partitions = partitionsColumn(threadCount, ySize);

        CyclicBarrier barrier = new CyclicBarrier(threadCount, () -> {
            synchronized (this) {
                getSharedBoard().printBoard();
            }
        });

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            int start = partitions[i][0];
            int end = partitions[i][1];
            threads[i] = new Thread(new ThreadWorker(barrier, start, end, this, config));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try{
                thread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
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
            int end;
            if(threatCount == 1){
                end = columnCount;
            }
            else {
                end = start + columnsPerThread - 1;
            }
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
            int columnCount;
            if(threadCount == 1){
                columnCount = colsCount;
            }
            else {
                columnCount = end - start + 1;
            }
            System.out.println("tid " + i +": cols " + partitions[i][0] + ":" + partitions[i][1] + " (" + columnCount +  ") rows: 0:" + (rowsCount - 1) + " (" + rowsCount  + ")");
        }
    }
    public boolean isMainThread() {
        return isMainThread;
    }

    public void setIsMainThread(boolean isMainThread) {
        this.isMainThread = isMainThread;
    }
}