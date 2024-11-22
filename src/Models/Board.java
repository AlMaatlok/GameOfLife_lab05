package Models;

public class Board {
    private Cell[][] board;
    private Coords cords;

    public Board(int rows, int cols) {
        board = new Cell[rows][cols];
        for(int r = 0; r < rows; r++)
            for(int c = 0; c < cols; c++)
                board[r][c] = new Cell(false);
    }

    public boolean getCellState(Coords coords) {
        int x = wrap(coords.getX(), board[0].length);
        int y = wrap(coords.getY(), board.length);
        return board[y][x].getIsAlive();
    }
    public void setCellState(Coords coords, boolean state) {
        int x = wrap(coords.getX(), board[0].length);
        int y = wrap(coords.getY(), board.length);
        board[y][x].setIsAlive(state);
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
        int rows = board[0].length;
        int cols = board.length;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newX = (x + i + rows) % rows;
                int newY = (y + j + cols) % cols;
                Coords newCoords = new Coords(newY, newX);
                if (getCellState(newCoords)) count++;
            }
        }
        return count;
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
    }
}
