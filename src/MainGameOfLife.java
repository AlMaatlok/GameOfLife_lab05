import Models.Board;
import Models.Coords;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainGameOfLife {
    public static void main(String[] args) {
        Board board = new Board(9, 10);
        Coords coords = new Coords(0, 0);
        board.setCellState(coords, true);
        Coords coords1 = new Coords(1, 0);
        board.setCellState(coords1, true);
        Coords coords2 = new Coords(8, 1);
        board.setCellState(coords2, true);
        Coords coords3 = new Coords(0, 9);
        board.setCellState(coords3, true);
        Coords newCoords = new Coords(1, 9);
        int count = board.countAllNeighbors(newCoords);
        board.printBoard();
        System.out.println(count);
    }
}