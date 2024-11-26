package Models;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Configuration {
    private int xSize;
    private int ySize;
    private int iterations;
    private int liveCellsCount;
    private ArrayList<Coords> aliveCells;

    public Configuration(){
        this.aliveCells = new ArrayList<>();
    }

    public Configuration(int xSize, int ySize, int iterations, int liveCellsCount) {
        this.setxSize(xSize);
        this.setySize(ySize);
        this.setIterations(iterations);
        this.setLiveCellsCount(liveCellsCount);
    }

    public void setxSize(int xSize) {
        validatePositiveInt(xSize, "xSize");
        this.xSize = xSize;
    }
    public int getxSize() {
        return xSize;
    }

    public void setySize(int ySize) {
        validatePositiveInt(ySize, "ySize");
        this.ySize = ySize;
    }
    public int getySize() {
        return ySize;
    }
    public void setIterations(int iterations) {
        validatePositiveInt(iterations, "iterations");
        this.iterations = iterations;
    }

    public void setLiveCellsCount(int liveCellsCount) {
        validateCoords(liveCellsCount, "liveCellsCount");
        this.liveCellsCount = liveCellsCount;
    }

    public void addAliveCells(String line) {
        String[] coords = line.strip().split(" ");
        if (coords.length != 2) {
            throw new IllegalArgumentException("Line must be in format '<number> <number>'");
        }
        int x = parseInt(coords[0], "Coord");
        int y = parseInt(coords[1], "Coord");
        Coords newCoord = new Coords(x, y);

        if (aliveCells.size() >= liveCellsCount) {
            throw new IllegalArgumentException("Too many initial coordinates.");
        }
        if(aliveCells.size() < liveCellsCount - 1){
            throw new IllegalArgumentException("Too few initial coordinates.");
        }
        if (aliveCells.contains(newCoord)) {
            throw new IllegalArgumentException("Duplicate coordinates: " + line);
        }
        this.aliveCells.add(newCoord);
    }
    public ArrayList<Coords> getAliveCells() {
        return aliveCells;
    }
    public void printAliveCells() {
        getAliveCells();
        for(Coords c : aliveCells) {
            System.out.printf(c.getX() + " " + c.getY() + " ");
        }
    }

    private void validatePositiveInt(int value, String argument) {
        if (value <= 0) {
            throw new IllegalArgumentException(argument + " must be positive");
        }
    }
    private void validateCoords(int value, String argument) {
        if (value < 0) {
            throw new IllegalArgumentException(argument + " must be >= 0");
        }
    }
    public int parseInt(String value, String argument) {
        try{
            int parsed = Integer.parseInt(value);
            if(argument.equals("Coord")){
                validateCoords(parsed, argument);
            }else {
                validatePositiveInt(parsed, argument);
            }
            return parsed;
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException(argument + " must be an integer");
        }
    }
}