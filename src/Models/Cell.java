package Models;

public class Cell {

    private boolean isAlive = false;
    private Coords coords;

    public static char aliveCellIcon = '@';
    public static char deadCellIcon = '-';

    public Cell(boolean isAlive, Coords coords) {
        this.isAlive = isAlive;
        this.coords = coords;
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
    public void setCoords(Coords coords){
        this.coords = coords;
    }
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
