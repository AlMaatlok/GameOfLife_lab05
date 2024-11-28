package Logic;

import Models.Board;
import Models.Cell;
import Models.Configuration;
import Models.Coords;

public class LogicHandler {
    //private Configuration config;

    public LogicHandler(){

    }
    public void goingThroughIterations(Configuration config, Board board){
        int iterations = config.getIterations();
        int xSize = config.getxSize();
        int ySize = config.getySize();

        for(int i = 0; i<iterations; i++){
            Board tempBoard = new Board(xSize, ySize, config);
            for(int j = 0; j<xSize; j++){
                for(int k = 0; k<ySize; k++){
                    Coords newCoords = new Coords(j, k);
                    boolean isAlive = board.getCellState(newCoords);
                    Cell newCell = new Cell(isAlive, newCoords );
                    int neighbours = board.countAllNeighbors(newCoords);

                    boolean newState = (isAlive && (neighbours == 2 || neighbours == 3)) || (!isAlive && neighbours == 3);
                    tempBoard.setCellState(newCoords, newState);
                    }
                }
            board.updateBoard(tempBoard);
            board.printBoard();
            }

        }
    }