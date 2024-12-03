package Models;

public class Board {
    private Cell[][] board;
    private Configuration config;

    public Board(int rows, int cols, Configuration config) {
        this.config = config;
        board = new Cell[rows][cols];
        for(int r = 0; r < rows; r++)
            for(int c = 0; c < cols; c++) {
                if (config.getAliveCells().contains(new Coords(r, c))){
                    board[r][c] = new Cell(true, new Coords(r,c));
                }
                else{
                    board[r][c] = new Cell(false, new Coords(r,c));
                }
            }

    }

    public boolean getCellState(Coords coords) {
        int x = wrap(coords.getX(), board.length);
        int y = wrap(coords.getY(), board[0].length);
        return board[x][y].getIsAlive();
    }
    public void setCellState(Coords coords, boolean state) {
        int x = wrap(coords.getX(), board.length);
        int y = wrap(coords.getY(), board[0].length);
        board[x][y].setIsAlive(state);
    }

    /*public boolean[][] getBoard() {
        return board;
    }
    public void setBoard(boolean[][] board) {
        this.board = board;
    }
    public void copyBoard(Board board) {
        this.board = board.getBoard();
    }*/
    private int wrap(int value, int max) {
        return (value + max) % max;
    }
    public int countAllNeighbors(Coords coords) {
        int count = 0;
        int x = coords.getX();
        int y = coords.getY();
        int rows = board.length;
        int cols = board[0].length;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newX = (x + i + rows) % rows;
                int newY = (y + j + cols) % cols;
                Coords newCoords = new Coords(newX, newY);
                if (getCellState(newCoords)) count++;
            }
        }
        return count;
    }

    public void updateBoard(Board tempBoard) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                boolean newState = tempBoard.getCellState(new Coords(i, j));
                board[i][j].setIsAlive(newState);
            }
        }
    }

    public void printBoard() {
        for(int j = 0; j < board.length; j++){
            for(int i = 0; i < board[j].length; i++) {
                {
                    System.out.print(board[j][i] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("***********************************************************************");
        System.out.println();
    }
    public Configuration getConfig(){
        return config;
    }
}