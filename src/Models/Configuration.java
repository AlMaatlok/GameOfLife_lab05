package Models;

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
    public int getIterations() {
        return iterations;
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
        if (aliveCells.contains(newCoord)) {
            throw new IllegalArgumentException("Duplicate coordinates: " + line);
        }
        this.aliveCells.add(newCoord);
    }
    public ArrayList<Coords> getAliveCells() {
        return aliveCells;
    }

    private static void validatePositiveInt(int value, String argument) {
        if (value <= 0) {
            throw new IllegalArgumentException(argument + " must be positive");
        }
    }
    private static void validateCoords(int value, String argument) {
        if (value < 0) {
            throw new IllegalArgumentException(argument + " must be >= 0");
        }
    }
    private static int parseInt(String value, String argument) {
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