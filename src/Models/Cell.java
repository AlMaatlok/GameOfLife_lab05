package Models;

public class Cell {

    private boolean isAlive = false;
    private final Coords coords;

    public static final char aliveCellIcon = '@';
    public static final char deadCellIcon = '-';

    // Konstruktor: Tworzy komórkę o określonym stanie i współrzędnych
    public Cell(boolean isAlive, Coords coords) {
        this.isAlive = isAlive;
        this.coords = coords;
    }
    // Konstruktor kopiujący: Tworzy kopię innej komórki
    public Cell(Cell cell) {
        this.isAlive = cell.isAlive;
        this.coords = cell.coords;
    }

    public boolean getIsAlive(){
        return isAlive;
    }
    public void setIsAlive(boolean isAlive){
        this.isAlive = isAlive;
    }
    public Coords getCoords(){
        return coords;
    }
    // Nadpisana metoda toString: Zwraca symbol reprezentujący stan komórki
    @Override
    public String toString(){
        return (this.isAlive) ? String.valueOf(Cell.aliveCellIcon) : String.valueOf(Cell.deadCellIcon);
    }

    @Override
    public Object clone() {
        Cell c = null;
        try {
            c = (Cell) super.clone();
        } catch (CloneNotSupportedException e) {
            c = new Cell(this.getIsAlive(), this.getCoords());
        }
        return c;
    }
}
